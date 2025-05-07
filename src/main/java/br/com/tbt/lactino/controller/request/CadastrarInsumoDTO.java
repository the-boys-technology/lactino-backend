package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.Insumo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CadastrarInsumoDTO(
    String nome,
    String categoria,
    String unidadeMedida,
    Double quantidadeTotal, // todo Validar se a quantidade total inicial é passada no cadastro ou começa em zero
    Double quantidadeMinima,
    LocalDate validade,
    BigDecimal preco,
    String fornecedor) {

  public Insumo toEntity() {
    return Insumo.builder()
        .nome(this.nome)
        .unidadeMedida(this.unidadeMedida)
        .quantidadeTotal(this.quantidadeTotal)
        .quantidadeMinima(this.quantidadeMinima)
        .validade(this.validade)
        .preco(this.preco)
        .fornecedor(this.fornecedor)
        .build();
  }
}
