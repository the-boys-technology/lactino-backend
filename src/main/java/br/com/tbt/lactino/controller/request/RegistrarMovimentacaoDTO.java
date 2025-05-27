package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.MovimentacaoEstoque;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RegistrarMovimentacaoDTO(
    @NotBlank(message = "É obrigatório informar o tipo da movimentação") String tipo,
    @NotNull(message = "É obrigatório informar a quantidade da movimentação")
        @PositiveOrZero(message = "A quantidade da movimentação não pode ser negativa")
        Double quantidade,
    @NotBlank(message = "É obrigatório informar o ID do insumo") String idInsumo,
    String descricao) { //todo Mais uma vez, validar a obrigatoriedade da descrição

  public MovimentacaoEstoque toEntity() {
    return MovimentacaoEstoque.builder()
        .quantidade(this.quantidade)
        .descricao(this.descricao)
        .build();
  }
}
