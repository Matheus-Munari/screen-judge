package com.filmes.avaliador.repository.specs;

import com.filmes.avaliador.model.ListaRecomendacoes;
import com.filmes.avaliador.model.ListaRecomendacoesFilme;
import com.filmes.avaliador.model.user.Users;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ListaRecomendacoesSpecs {

    public static Specification<ListaRecomendacoes> nomeUsuarioLike(String nomeUsuario){
        return (root, query, cb) -> {
            Join<ListaRecomendacoes, Users> usersJoin = root.join("users");
            return cb.like(cb.lower(usersJoin.get("nome")), "%" + nomeUsuario.toLowerCase() + "%");
        };
    }

    public static Specification<ListaRecomendacoes> generoEquals(String genero){
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }

    public static Specification<ListaRecomendacoes> nomeLike(String nome){
        return (root, query, cb) -> cb.like(root.get("nome"), "%" + nome + "%");
    }

    public static Specification<ListaRecomendacoesFilme> idEquals(Long id){
        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }
}
