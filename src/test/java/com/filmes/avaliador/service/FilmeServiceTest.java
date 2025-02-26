package com.filmes.avaliador.service;

import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.exception.NotFoundException;
import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.repository.FilmeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmeServiceTest {

    @InjectMocks
    private FilmeService service;

    @Mock
    private FilmeRepository repository;

    private Filme filme;

    private List<Filme> filmes;

    @BeforeEach
    public void filmeCadastrado(){
        filme = Filme.builder()
                .id(1)
                .titulo("Ilha do Medo")
                .diretor("Martin Scorsese")
                .anoLancamento(Year.of(2010))
                .dataCriada(LocalDate.of(2025, 2, 14))
                .dataAlterada(LocalDate.of(2025, 2, 14))
                .genero("Suspense")
                .urlImagem("https://www.papodecinema.com.br/wp-content/uploads/2012/04/20180529-download.jpg")
                .build();

        filmes = new ArrayList<>(List.of(filme));
    }

    @Nested
    @DisplayName("Serviço de cadastrar novo filme test")
    class CadastroFilmeTest{
        @Test
        @DisplayName("Cadastrar um Filme")
        void dadoQueNaoPossuoUmFilmeCadastrado_QuandoTentarCadastrar_EntaoDevoCadastrarComSucesso(){
            Filme novoFilme = Filme.builder()
                    .id(1)
                    .titulo("Ilha do Medo")
                    .diretor("Martin Scorsese")
                    .anoLancamento(Year.of(2010))
                    .dataCriada(LocalDate.of(2025, 2, 14))
                    .dataAlterada(LocalDate.of(2025, 2, 14))
                    .genero("Suspense")
                    .urlImagem("https://www.papodecinema.com.br/wp-content/uploads/2012/04/20180529-download.jpg")
                    .build();

            Mockito.when(repository.findByTituloAndDiretorAndAnoLancamento(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
            Mockito.when(repository.save(Mockito.any(Filme.class))).thenReturn(novoFilme);

            service.cadastrarFilme(novoFilme);

            Mockito.verify(repository,
                    Mockito.times(1)).save(Mockito.any(Filme.class));
        }

        @Test
        @DisplayName("Tentar cadastrar um filme já cadastrado teste")
        void dadoQueJaPossuoUmFilmeCadastrado_QuandoTentoCadastrarDeNovo_EntaoReceboErroTest(){

            Filme novoFilme = Filme.builder()
                    .id(1)
                    .titulo("Ilha do Medo")
                    .diretor("Martin Scorsese")
                    .anoLancamento(Year.of(2010))
                    .dataCriada(LocalDate.of(2025, 2, 14))
                    .dataAlterada(LocalDate.of(2025, 2, 14))
                    .genero("Suspense")
                    .urlImagem("https://www.papodecinema.com.br/wp-content/uploads/2012/04/20180529-download.jpg")
                    .build();

            Mockito.when(repository.findByTituloAndDiretorAndAnoLancamento(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Optional.of(filme));

            assertThrows(
                    ConflitoException.class,
                    () -> service.cadastrarFilme(novoFilme)
            );

            Mockito.verify(repository,
                    Mockito.times(0)).save(Mockito.any(Filme.class));
        }


    }

    @Nested
    @DisplayName("Serviço de atualizar filme test")
    class AtualizarUsuarioTest{
        @Test
        @DisplayName("Atualizar um Filme")
        void dadoQuePossuoUmFilmeCadastrado_QuandoTentarAtualizar_EntaoDevoAtualizarComSucessoTest(){

            Filme novoFilme = Filme.builder()
                    .id(1)
                    .titulo("Ilha do Medo")
                    .diretor("Martin Scorsese")
                    .anoLancamento(Year.of(2010))
                    .dataCriada(LocalDate.of(2025, 2, 14))
                    .dataAlterada(LocalDate.of(2025, 2, 14))
                    .genero("Suspense")
                    .urlImagem("https://www.papodecinema.com.br/wp-content/uploads/2012/04/20180529-download.jpg")
                    .build();

            Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(filme));
            Mockito.when(repository.save(Mockito.any(Filme.class))).thenReturn(novoFilme);

            service.atualizarFilme(novoFilme, novoFilme.getId());

            Mockito.verify(repository,
                    Mockito.times(1)).save(Mockito.any(Filme.class));
        }

        @Test
        @DisplayName("Tentar atualizar um filme não cadastrado teste")
        void dadoQueJaPossuoUmFilmeCadastrado_QuandoTentoCadastrarDeNovo_EntaoReceboErroTest(){

            Filme novoFilme = Filme.builder()
                    .id(1)
                    .titulo("Ilha do Medo")
                    .diretor("Martin Scorsese")
                    .anoLancamento(Year.of(2010))
                    .dataCriada(LocalDate.of(2025, 2, 14))
                    .dataAlterada(LocalDate.of(2025, 2, 14))
                    .genero("Suspense")
                    .urlImagem("https://www.papodecinema.com.br/wp-content/uploads/2012/04/20180529-download.jpg")
                    .build();

            Mockito.when(repository.findByTituloAndDiretorAndAnoLancamento(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

            assertThrows(
                    NotFoundException.class,
                    () -> service.atualizarFilme(novoFilme, novoFilme.getId())
            );

            Mockito.verify(repository,
                    Mockito.times(0)).save(Mockito.any(Filme.class));
        }


    }

    @Nested
    @DisplayName("Serviço de deletar filme test")
    class DeletarFilmeTest{
        @Test
        @DisplayName("Deletar um Filme")
        void dadoQuePossuoUmFilmeCadastrado_QuandoTentarDeletar_EntaoDevoDeletarComSucessoTest(){

            Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(filme));
            Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());

            service.deletarFilme(1);

            Mockito.verify(repository,
                    Mockito.times(1)).deleteById(Mockito.anyInt());
        }

        @Test
        @DisplayName("Tentar Deletar um filme não cadastrado teste")
        void dadoQueNaoPossuoUmFilmeCadastrado_QuandoTentoDeletar_EntaoReceboErroTest(){

            Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
            assertThrows(
                    NotFoundException.class,
                    () -> service.deletarFilme(1)
            );

            Mockito.verify(repository,
                    Mockito.times(0)).save(Mockito.any(Filme.class));
        }


    }

    @Nested
    @DisplayName("Serviço de buscar filmes test")
    class BuscarFilmeTest{
        @Test
        @DisplayName("Buscar um Filme")
        void dadoQuePossuoUmFilmeCadastrado_QuandoTentarBuscar_EntaoDevoReceberOUsuarioBuscadoTest(){

            Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(filme));

            Filme filmeBuscado = service.buscarPorId(1);

            assertEquals(filmeBuscado, filme);

            Mockito.verify(repository,
                    Mockito.times(1)).findById(Mockito.anyInt());
        }

        @Test
        @DisplayName("Buscar todos os filmes")
        void dadoQueNaoPossuoUmFilmeCadastrado_QuandoTentoBuscar_EntaoReceboErroTest(){

            Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(filmes);
            Page<Filme> filmesBuscados = service.buscarFilmes(null, null, null, null, 0, 10);

            assertEquals(filmesBuscados.getTotalElements(), 1);

            Mockito.verify(repository,
                    Mockito.times(1)).findAll(Mockito.any(Example.class));
        }


    }

}