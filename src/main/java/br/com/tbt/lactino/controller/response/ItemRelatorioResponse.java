package br.com.tbt.lactino.controller.response;

import java.math.BigDecimal;

public record ItemRelatorioResponse(
        String nomeProduto,
        String categoria,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
}