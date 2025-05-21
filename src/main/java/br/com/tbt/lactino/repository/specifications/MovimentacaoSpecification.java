package br.com.tbt.lactino.repository.specifications;

import br.com.tbt.lactino.controller.request.MovimentacaoFiltro;
import br.com.tbt.lactino.model.MovimentacaoEstoque;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MovimentacaoSpecification {
  public static Specification<MovimentacaoEstoque> comFiltros(MovimentacaoFiltro filtro) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filtro.tipo() != null) {
        predicates.add(cb.equal(root.get("tipo"), filtro.tipo()));
      }

      if (filtro.dataInicial() != null) {
        predicates.add(cb.greaterThanOrEqualTo(root.get("dataMovimentacao"), filtro.dataInicial()));
      }

      if (filtro.dataFinal() != null) {
        predicates.add(cb.lessThanOrEqualTo(root.get("dataMovimentacao"), filtro.dataFinal()));
      }

      if (filtro.insumoID() != null) {
        predicates.add(cb.equal(root.get("insumo").get("id"), filtro.insumoID()));
      }

      if (filtro.usuario() != null) {
        predicates.add(cb.equal(root.get("insumo").get("usuario"), filtro.usuario()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
