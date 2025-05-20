package br.com.tbt.lactino.repository.specifications;

import br.com.tbt.lactino.controller.request.LaticinioFiltro;
import br.com.tbt.lactino.model.Laticinio;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class LaticinioSpecification {

    public static Specification<Laticinio> filtrar(LaticinioFiltro filtro) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get("usuario"), filtro.usuario()));

            if (filtro.tipo() != null && !filtro.tipo().isBlank()) {
                predicates.add(builder.like(builder.lower(root.get("tipoProduto")), "%" + filtro.tipo().toLowerCase() + "%"));
            }

            if (filtro.status() != null) {
                predicates.add(builder.equal(root.get("status"), filtro.status()));
            }

            if (filtro.leiteUtilizadoId() != null) {
                predicates.add(builder.equal(root.get("leiteUtilizado").get("id"), filtro.leiteUtilizadoId()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
