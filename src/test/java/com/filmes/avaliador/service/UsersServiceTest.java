package com.filmes.avaliador.service;

import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.model.Users;
import com.filmes.avaliador.repository.UsersRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Nested
    @DisplayName("Serviço de cadastrar novo usuario test")
    class CadastroUsuarioTest{
        @Test
        @DisplayName("Tentar cadastrar um user já cadastrado teste")
        void dadoQueJaPossuoUmUserCadastrado_QuandoTentoCadastrarDeNovo_EntaoReceboErroTest(){

            Users novoUser = new Users(
                    UUID.randomUUID(),
                    "Matheus Munari",
                    "matheus.denani@test.com",
                    "Senha@123",
                    LocalDate.of(2004, 04, 21),
                    LocalDate.of(2025, 02, 13),
                    LocalDate.of(2025, 02, 13),
                    true
            );

            Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

            assertThrows(
                    ConflitoException.class,
                    () -> service.cadastrarUsuario(novoUser)
            );
            Mockito.verify(repository,
                    Mockito.times(0)).save(Mockito.any(Users.class));
        }

        @Test
        @DisplayName("Cadastrar um usuario")
        void dadoQueNaoPossuoUmUserCadastrado_QuandoTentarCadastrar_EntaoDevoCadastrarComSucesso(){
            Users novoUser = new Users(
                    UUID.randomUUID(),
                    "Matheus Munari",
                    "matheus.munari@test.com",
                    "Senha@123",
                    LocalDate.of(2004, 04, 21),
                    LocalDate.of(2025, 02, 13),
                    LocalDate.of(2025, 02, 13),
                    true
            );

            Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
            Mockito.when(repository.save(Mockito.any(Users.class))).thenReturn(novoUser);

            service.cadastrarUsuario(novoUser);

            Mockito.verify(repository,
                    Mockito.times(1)).save(Mockito.any(Users.class));
        }
    }
}