package br.com.tbt.lactino.repository.specifications;

import br.com.tbt.lactino.controller.request.LeiteFiltro;
import br.com.tbt.lactino.model.Leite;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class LeiteSpecification {

    public static Specification<Leite> filtrar(LeiteFiltro filtro) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.status() != null) {
                predicates.add(builder.equal(root.get("status"), filtro.status()));
            }

            if (filtro.origem() != null && !filtro.origem().isBlank()) {
                predicates.add(builder.like(
                        builder.lower(root.get("origem")),
                        "%" + filtro.origem().toLowerCase() + "%"
                ));
            }

            if (filtro.turno() != null) {
                predicates.add(builder.equal(root.get("turno"), filtro.turno()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
