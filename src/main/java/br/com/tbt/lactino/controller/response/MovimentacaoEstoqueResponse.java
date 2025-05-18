package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.MovimentacaoEstoque;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record MovimentacaoEstoqueResponse(
    UUID id,
    LocalDateTime dataMovimentacao,
    String tipo,
    Double quantidade,
    UUID idInsumo,
    String nomeInsumo,
    String descricao) {

  public MovimentacaoEstoqueResponse(MovimentacaoEstoque movimentacao) {
    this(
        movimentacao.getId(),
        movimentacao.getDataMovimentacao(),
        movimentacao.getTipo().getDescricao(),
        movimentacao.getQuantidade(),
        movimentacao.getInsumo().getId(),
        movimentacao.getInsumo().getNome(),
        movimentacao.getDescricao());
  }
}
