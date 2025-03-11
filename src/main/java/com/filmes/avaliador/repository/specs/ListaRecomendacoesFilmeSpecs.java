package com.filmes.avaliador.repository.specs;

import com.filmes.avaliador.model.ListaRecomendacoes;
import com.filmes.avaliador.model.ListaRecomendacoesFilme;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ListaRecomendacoesFilmeSpecs {

    public static Specification<ListaRecomendacoesFilme> idEquals(Long id){
        return (root, query, cb) -> {
            Join<ListaRecomendacoesFilme, ListaRecomendacoes> joinList = root.join("listaRecomendacoes");
            return cb.equal(joinList.get("id"), id);
        };

    }
}
