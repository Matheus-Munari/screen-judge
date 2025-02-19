package com.filmes.avaliador.service;

import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.exception.NotFoundException;
import com.filmes.avaliador.model.user.Users;
import com.filmes.avaliador.repository.UsersRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceTest {

    @InjectMocks
    private UsersService service;

    @Mock
    private UsersRepository repository;

    private Users user;

    @BeforeEach
    void criarNovoUSer(){
        MockitoAnnotations.openMocks(this);
        user = new Users(
                UUID.randomUUID(),
                "Matheus Munari",
                "matheus.denani@test.com",
                "Senha@123",
                LocalDate.of(2004, 04, 21),
                LocalDate.of(2025, 02, 13),
                LocalDate.of(2025, 02, 13),
                true
        );
    }

//    @Nested
//    @DisplayName("Serviço de cadastrar novo usuario test")
//    class CadastroUsuarioTest{
//        @Test
//        @DisplayName("Tentar cadastrar um user já cadastrado teste")
//        void dadoQueJaPossuoUmUserCadastrado_QuandoTentoCadastrarDeNovo_EntaoReceboErroTest(){
//
//            Users novoUser = new Users(
//                    UUID.randomUUID(),
//                    "Matheus Munari",
//                    "matheus.denani@test.com",
//                    "Senha@123",
//                    LocalDate.of(2004, 04, 21),
//                    LocalDate.of(2025, 02, 13),
//                    LocalDate.of(2025, 02, 13),
//                    true
//            );
//
//            Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
//
//            assertThrows(
//                    ConflitoException.class,
//                    () -> service.cadastrarUsuario(novoUser)
//            );
//            Mockito.verify(repository,
//                    Mockito.times(0)).save(Mockito.any(Users.class));
//        }
//
//        @Test
//        @DisplayName("Cadastrar um usuario")
//        void dadoQueNaoPossuoUmUserCadastrado_QuandoTentarCadastrar_EntaoDevoCadastrarComSucesso(){
//            Users novoUser = new Users(
//                    UUID.randomUUID(),
//                    "Matheus Munari",
//                    "matheus.munari@test.com",
//                    "Senha@123",
//                    LocalDate.of(2004, 04, 21),
//                    LocalDate.of(2025, 02, 13),
//                    LocalDate.of(2025, 02, 13),
//                    true
//            );
//
//            Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
//            Mockito.when(repository.save(Mockito.any(Users.class))).thenReturn(novoUser);
//
//            service.cadastrarUsuario(novoUser);
//
//            Mockito.verify(repository,
//                    Mockito.times(1)).save(Mockito.any(Users.class));
//        }
//    }

    @Nested
    @DisplayName("Serviço de atualizar usuario test")
    class AtualizacaoUsuarioTest{
        @Test
        @DisplayName("Tentar atualizar um user não cadastrado teste")
        void dadoQueNaoPossuoUmUserCadastrado_QuandoTentoAtualizarDeNovo_EntaoReceboErroTest(){

            Users userNovo = new Users();

            userNovo.setNome("Matheus Munari");
            userNovo.setEmail("matheus.munari@test.com");
            userNovo.setSenha("Senha@123");
            userNovo.setDataNascimento(LocalDate.of(2004, 04, 21));
            userNovo.setDataCriada(LocalDate.of(2025, 02, 13));
            userNovo.setDataAtualizada(LocalDate.of(2025, 02, 13));
            userNovo.setAtivo(true);

            Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> service.atualizarUsuario(userNovo, UUID.randomUUID())
            );
            Mockito.verify(repository,
                    Mockito.times(0)).save(Mockito.any(Users.class));
        }

        @Test
        @DisplayName("Atualizar um usuario")
        void dadoQuePossuoUmUserCadastrado_QuandoTentarAtualizar_EntaoDevoCadastrarComSucesso(){
            Users novoUser = Users.builder()
                    .nome("Matheus Munari")
                    .id(UUID.randomUUID())
                    .email("matheus.munari@teste.com.br")
                    .senha("Senha@1234")
                    .dataNascimento(LocalDate.of(2004, 04, 21))
                    .dataCriada(LocalDate.of(2025, 2, 13))
                    .dataAtualizada(LocalDate.of(2025, 2, 13)).build();

            Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(user));
            Mockito.when(repository.save(Mockito.any(Users.class))).thenReturn(novoUser);

            service.atualizarUsuario(novoUser, novoUser.getId());

            Mockito.verify(repository,
                    Mockito.times(1)).save(Mockito.any(Users.class));
        }
    }

    @Nested
    @DisplayName("Serviço de deletar usuario test")
    class DelecaoUsuarioTest{
        @Test
        @DisplayName("Tentar deletar um user não cadastrado teste")
        void dadoQueNaoPossuoUmUserCadastrado_QuandoTentoDeletarDeNovo_EntaoReceboErroTest(){

            Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> service.deletarUsuario(UUID.randomUUID())
            );
            Mockito.verify(repository,
                    Mockito.times(0)).save(Mockito.any(Users.class));
        }

        @Test
        @DisplayName("Deletar um usuario já deletado test")
        void dadoQuePossuoUmUserCadastradoJaDeletado_QuandoTentarAtualizar_EntaoDevoReceberUmErroTest(){

            Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(user));
//            Mockito.when(repository.save(Mockito.any(Users.class))).thenReturn(user);
            user.setAtivo(false);

            assertThrows(
                    ConflitoException.class,
                    () -> service.deletarUsuario(UUID.randomUUID())
            );

            Mockito.verify(repository,
                    Mockito.times(0)).save(Mockito.any(Users.class));
        }

        @Test
        @DisplayName("Deletar um usuario")
        void dadoQuePossuoUmUserCadastrado_QuandoTentarDeletar_EntaoDevoDeletarComSucessoTest(){

            Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(user));
            Mockito.when(repository.save(Mockito.any(Users.class))).thenReturn(user);

            service.deletarUsuario(UUID.randomUUID());

            Mockito.verify(repository,
                    Mockito.times(1)).save(Mockito.any(Users.class));
        }

    }
}