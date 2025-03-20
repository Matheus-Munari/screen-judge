package com.filmes.avaliador.service;

import com.filmes.avaliador.dto.response.email.ComentarioAvaliacaoEmailDTO;
import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.mapper.AvaliacaoMapper;
import com.filmes.avaliador.model.Avaliacao;
import com.filmes.avaliador.model.Comentario;
import com.filmes.avaliador.model.ComentarioAvaliacao;
import com.filmes.avaliador.model.ListaRecomendacoesFilme;
import com.filmes.avaliador.model.user.Users;
import com.filmes.avaliador.repository.ComentarioAvaliacaoRepository;
import com.filmes.avaliador.repository.ComentarioRepository;
import com.filmes.avaliador.repository.specs.ComentarioSpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.filmes.avaliador.repository.specs.ComentarioSpecs.orderByAsc;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComentarioAvaliacaoService {

    private final ComentarioAvaliacaoRepository comentarioAvaliacaoRepository;
    private final ComentarioRepository comentarioRepository;
    private final UsersService usersService;
    private final AvaliacaoService avaliacaoService;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    public ComentarioAvaliacao cadastrarNovoComentario(Integer idAvaliacao, String comentario, UUID idUsuario){
        Users usuario = usersService.usuarioPorId(idUsuario);

        Avaliacao avaliacao = avaliacaoService.buscarPorId(idAvaliacao);

        Optional<Comentario> comentarioBuscado = comentarioRepository.findByUsuario(usuario);
        if(comentarioBuscado.isPresent()){
            throw new ConflitoException("Usuário já comentou nesta avaliação");
        }

        Comentario novoComentario = new Comentario();
        novoComentario.setComentario(comentario);
        novoComentario.setUsuario(usuario);
        novoComentario.setDataComentario(LocalDateTime.now());

        novoComentario = comentarioRepository.save(novoComentario);

        ComentarioAvaliacao comentarioAvaliacao = new ComentarioAvaliacao();
        comentarioAvaliacao.setAvaliacao(avaliacao);
        comentarioAvaliacao.setComentario(novoComentario);

        ComentarioAvaliacao comentarioSalvo = comentarioAvaliacaoRepository.save(comentarioAvaliacao);

        ComentarioAvaliacaoEmailDTO emailDTO = AvaliacaoMapper.toComentarioAvaliacaoEmailDto(comentarioSalvo);

        kafkaTemplate.send("comentario_avaliacao_email", emailDTO);
        return comentarioSalvo;
    }

    public Page<ComentarioAvaliacao> buscarComentariosDeAvaliacao(Integer idAvaliacao, boolean orderByAsc, Integer pagina, Integer tamanhoPagina){

        Specification<ComentarioAvaliacao> specs = Specification.where((root, query, cb) -> cb.conjunction() );

        specs = specs.and(orderByAsc(orderByAsc));

        Pageable page = PageRequest.of(pagina, tamanhoPagina);

        return comentarioAvaliacaoRepository.findAll(specs, page);

    }

    public ComentarioAvaliacao buscarComentarioPorId(Integer idComentario){
        return comentarioAvaliacaoRepository.findByComentarioId(idComentario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentário não encontrado"));
    }

    @Transactional
    public void deletarComentario(Integer idComentario, Authentication authentication){
        ComentarioAvaliacao comentario = buscarComentarioPorId(idComentario);
        String userName = authentication.getName();

        if(!userName.equals(comentario.getComentario().getUsuario().getEmail()) || !userName.equals(comentario.getAvaliacao().getUsuario().getEmail())){
            log.info("Usuário {} tem permissão para deletar este comentário", userName);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não tem permissão para deletar este comentário");
        }

        comentarioAvaliacaoRepository.delete(comentario);
        comentarioRepository.delete(comentario.getComentario());

    }

    public ComentarioAvaliacao atualizarComentario(Integer idComentario,String comentario, String idUsuario, Authentication authentication){

        Optional<Comentario> comentarioBuscado = comentarioRepository.findById(idComentario);
        Optional<ComentarioAvaliacao> comentarioAvaliacaoBuscado = comentarioAvaliacaoRepository.findByComentarioId(idComentario);
        Users usuario = usersService.usuarioPorId(UUID.fromString(idUsuario));

        if(comentarioBuscado.isEmpty() || comentarioAvaliacaoBuscado.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentário não encontrado");
        }

        if(!authentication.getName().equals(usuario.getEmail())){
            log.info("Usuário {} tem permissão para atualizar este comentário", authentication.getName());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não tem permissão para atualizar este comentário");
        }

        Comentario comentarioAtualizado = comentarioBuscado.get();
        comentarioAtualizado.setComentario(comentario);

        comentarioRepository.save(comentarioAtualizado);

        ComentarioAvaliacao comentarioAvaliacao = comentarioAvaliacaoBuscado.get();
        comentarioAvaliacao.setComentario(comentarioAtualizado);

        return comentarioAvaliacaoRepository.save(comentarioAvaliacao);

    }

}
