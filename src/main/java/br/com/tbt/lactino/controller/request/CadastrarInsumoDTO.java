package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.Insumo;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CadastrarInsumoDTO(
    @NotBlank(message = "É obrigatório informar o nome do insumo") String nome,
    @NotBlank(message = "É obrigatório informar a categoria do insumo") String categoria,
    @NotBlank(message = "É obrigatório informar a unidade de medida do insumo")
        String unidadeMedida,
    @NotNull(message = "É obrigatório informar a quantidade total do insumo")
        @PositiveOrZero(message = "A quantidade total do insumo não pode ser negativa")
        Double
            quantidadeTotal, // todo Validar se a quantidade total inicial é passada no cadastro o começa em zero
    @NotNull(message = "É obrigatório informar a quantidade mínima do insumo")
        @PositiveOrZero(message = "A quantidade mínima do insumo não pode ser negativa")
        Double quantidadeMinima,
    @FutureOrPresent(message = "A data de validade do insumo não pode ser uma data passada")
        LocalDate validade,
    @NotNull(message = "É obrigatório informar o preço do insumo") BigDecimal preco,
    @NotBlank(message = "É obrigatório informar o fornecedor do insumo") String fornecedor) {

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
