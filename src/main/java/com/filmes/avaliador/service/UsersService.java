package com.filmes.avaliador.service;

import com.filmes.avaliador.dto.response.email.EmailMessageDTO;
import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.exception.NotFoundException;
import com.filmes.avaliador.model.Autenticacao2Fatores;
import com.filmes.avaliador.model.user.Users;
import com.filmes.avaliador.repository.Autenticacao2FatoresRepository;
import com.filmes.avaliador.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UsersService {

    private final UsersRepository repository;
    private final Autenticacao2FatoresRepository doisfatoresRepository;
    private List<Users> usuariosParaCriar;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UsersService(UsersRepository repository, Autenticacao2FatoresRepository doisfatoresRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.doisfatoresRepository = doisfatoresRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.usuariosParaCriar = new ArrayList<>();
    }

    public List<Users> listarUsuarios(String nome){

        Users users = new Users();
        users.setNome(nome);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Users> usersExample = Example.of(users, matcher);
        return repository.findAll(usersExample);
    }

    public Users cadastrarUsuario(Users usuario){

        UserDetails usuarioPossivel = repository.findByEmail(usuario.getEmail());

        if(usuarioPossivel!=null){
            throw new ConflitoException("Usuário já cadastrado");
        }

        String senhaEncriptada = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setSenha(senhaEncriptada);

        usuario.setAtivo(true);
        usuario.setDataAtualizada(LocalDate.now());
        usuario.setDataCriada(LocalDate.now());

        return repository.save(usuario);
    }

    public void atualizarUsuario(Users user, UUID id){

        Optional<Users> usuarioCadastrado = repository.findById(id);

        if(usuarioCadastrado.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }

        user.setId(id);

        repository.save(user);
    }

    @Transactional
    public void deletarUsuario(UUID id){

        Optional<Users> usuarioADeletar = repository.findById(id);

        if(usuarioADeletar.isEmpty()){
            throw new NotFoundException("Usuário não cadastrado");
        }

        if(!usuarioADeletar.get().getAtivo()){
            throw new ConflitoException("Usuário já deletado");
        }

        usuarioADeletar.get().setAtivo(false);

        repository.save(usuarioADeletar.get());
    }

    public Users usuarioPorId(UUID id){

        Optional<Users> usuarioBuscado = repository.findById(id);

        if(usuarioBuscado.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }

        return usuarioBuscado.get();

    }

    public UserDetails carregarPorEmail(String email){
        UserDetails usuario =  repository.findByEmail(email);
        if(usuario == null){
            throw new NotFoundException("Usuário não encontrado");
        }
        return usuario;
    }

    public Integer gerarCodigoAutenticacao(Users usuario){
        Integer indiceUsuario = usuariosParaCriar.size();
        usuariosParaCriar.add(usuario);
        Integer numeroAleatorio = ThreadLocalRandom.current().nextInt(100_000, 1_000_000);
        String codigo = numeroAleatorio.toString();
        String codigoEncriptado = new BCryptPasswordEncoder().encode(codigo);

        Autenticacao2Fatores autenticacao = new Autenticacao2Fatores();
        autenticacao.setCodigo(codigoEncriptado);
        doisfatoresRepository.save(autenticacao);
        EmailMessageDTO email = EmailMessageDTO.builder()
                .destinatario(usuario.getEmail())
                .tipo("VALIDACAO_EMAIL")
                .assunto("Código de verificação de conta")
                .corpo("Seu código de verificação de email é: " + codigo)
                .build();
        kafkaTemplate.send("email-sender", email);
        return indiceUsuario;
    }
}
