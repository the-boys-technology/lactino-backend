package br.com.tbt.lactino.model.enums;

import lombok.Getter;

@Getter
public enum CategoriaInsumoEnum {
  RACAO("Ração"),
  REMEDIO("Remédio"),
  OUTROS("Outros");

  private final String descricao;

  CategoriaInsumoEnum(String descricao) {
    this.descricao = descricao;
  }
}
