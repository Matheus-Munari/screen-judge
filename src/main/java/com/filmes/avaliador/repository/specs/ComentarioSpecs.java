package com.filmes.avaliador.repository.specs;

import com.filmes.avaliador.model.Comentario;
import com.filmes.avaliador.model.ComentarioAvaliacao;
import com.filmes.avaliador.model.ListaRecomendacoes;
import com.filmes.avaliador.model.ListaRecomendacoesFilme;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ComentarioSpecs {

    public static Specification<ComentarioAvaliacao> orderByAsc(Boolean orderByAsc){
        return (root, query, cb) -> {
            Join<ComentarioAvaliacao, Comentario> joinList = root.join("comentario");
            if(orderByAsc){
                cb.asc(joinList.get("dataComentario"));
            }else {
                cb.desc(joinList.get("dataComentario"));
            }
            return cb.conjunction();
        };

    }

}
