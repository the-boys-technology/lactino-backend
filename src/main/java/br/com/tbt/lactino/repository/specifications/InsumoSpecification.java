package br.com.tbt.lactino.repository.specifications;

import br.com.tbt.lactino.controller.request.InsumoFiltro;
import br.com.tbt.lactino.model.Insumo;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class InsumoSpecification {

  public static Specification<Insumo> comFiltros(InsumoFiltro filtro) {
    return (root, query, cb) -> {
      List<Predicate> predicados = new ArrayList<>();

      if (filtro.nome() != null && !filtro.nome().isEmpty()) {
        predicados.add(
            cb.like(cb.lower(root.get("nome")), "%" + filtro.nome().toLowerCase() + "%"));
      }

      if (filtro.categoria() != null) {
        predicados.add(cb.equal(root.get("categoria"), filtro.categoria()));
      }

      if (filtro.status() != null) {
        predicados.add(cb.equal(root.get("status"), filtro.status()));
      }

      if (Boolean.TRUE.equals(filtro.emBaixoEstoque())) {
        predicados.add(cb.lessThan(root.get("quantidadeTotal"), root.get("quantidadeMinima")));
      }

      return cb.and(predicados.toArray(new Predicate[0]));
    };
  }
}
