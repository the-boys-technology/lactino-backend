package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Insumo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record InsumoResponse(
    UUID id,
    String nome,
    String categoria,
    String unidadeMedida,
    Double quantidadeTotal,
    Double quantidadeMinima,
    LocalDate validade,
    BigDecimal preco,
    String fornecedor,
    String status) {

  public InsumoResponse(Insumo insumo) {
    this(
        insumo.getId(),
        insumo.getNome(),
        insumo.getCategoria().getDescricao(),
        insumo.getUnidadeMedida(),
        insumo.getQuantidadeTotal(),
        insumo.getQuantidadeMinima(),
        insumo.getValidade(),
        insumo.getPreco(),
        insumo.getFornecedor(),
        insumo.getStatus().getDescricao());
  }
}
