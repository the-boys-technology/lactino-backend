package br.com.tbt.lactino.model.enums;

import lombok.Getter;

@Getter
public enum TipoMovimentacaoEstoque {
  ENTRADA("Entrada"),
  CONSUMO("Consumo"),
  AJUSTE("Ajuste");

  private final String descricao;

  TipoMovimentacaoEstoque(String descricao) {
    this.descricao = descricao;
  }
}
