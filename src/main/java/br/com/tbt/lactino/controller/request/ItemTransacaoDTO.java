package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.enums.CategoriaProduto;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemTransacaoDTO(
        @NotNull UUID produtoId,
        @NotNull CategoriaProduto categoria,
        @NotNull Integer quantidade,
        @NotNull BigDecimal precoUnitario,
        @NotNull String unidadeDeMedida
) {
}