package br.com.tbt.lactino.model.enums;

import lombok.Getter;

@Getter
public enum StatusInsumoEnum {
  ATIVO("Ativo"),
  VENCIDO("Vencido"),
  ESGOTADO("Esgotado");

  private final String descricao;

  StatusInsumoEnum(String descricao) {
    this.descricao = descricao;
  }
}
