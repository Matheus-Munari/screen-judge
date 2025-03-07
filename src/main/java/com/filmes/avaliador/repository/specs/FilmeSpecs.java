package com.filmes.avaliador.repository.specs;

import com.filmes.avaliador.model.Filme;
import org.springframework.data.jpa.domain.Specification;

import java.time.Year;

public class FilmeSpecs {

    public static Specification<Filme> tituloLike(String titulo){
        return ((root, query, cb) -> cb.like(root.get("titulo"), "%" + titulo + "%"));
    }

    public static Specification<Filme> diretorLike(String diretor){
        return (root, query, cb) -> cb.like(cb.lower(root.get("diretor")), "%" + diretor.toLowerCase() + "%");
    }

    public static Specification<Filme> generoEqual(String genero){
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }

    public static Specification<Filme> anoLancamentoEqual(Year anoLancamento){
        return (root, query, cb) -> cb.equal(cb.function("YEAR", Integer.class, root.get("dataLancamento")), anoLancamento.getValue());
    }

}
