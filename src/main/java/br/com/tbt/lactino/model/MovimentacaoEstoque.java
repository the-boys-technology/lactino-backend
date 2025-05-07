package br.com.tbt.lactino.model;

import br.com.tbt.lactino.model.enums.TipoMovimentacaoEstoque;

import java.time.LocalDateTime;
import java.util.UUID;

public class MovimentacaoEstoque {
  private UUID id;
  private LocalDateTime dataMovimentacao;
  private TipoMovimentacaoEstoque tipo;
  private Double quantidade;
  private Insumo insumo;
  private String descricao;
}
