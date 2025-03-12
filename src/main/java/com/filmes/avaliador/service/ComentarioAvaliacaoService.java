package com.filmes.avaliador.service;

import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.model.Avaliacao;
import com.filmes.avaliador.model.Comentario;
import com.filmes.avaliador.model.ComentarioAvaliacao;
import com.filmes.avaliador.model.ListaRecomendacoesFilme;
import com.filmes.avaliador.model.user.Users;
import com.filmes.avaliador.repository.ComentarioAvaliacaoRepository;
import com.filmes.avaliador.repository.ComentarioRepository;
import com.filmes.avaliador.repository.specs.ComentarioSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
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
public class ComentarioAvaliacaoService {

    private final ComentarioAvaliacaoRepository comentarioAvaliacaoRepository;
    private final ComentarioRepository comentarioRepository;
    private final UsersService usersService;
    private final AvaliacaoService avaliacaoService;


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

        return comentarioAvaliacaoRepository.save(comentarioAvaliacao);
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
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não tem permissão para deletar este comentário");
        }

        comentarioAvaliacaoRepository.delete(comentario);
        comentarioRepository.delete(comentario.getComentario());

    }

}
