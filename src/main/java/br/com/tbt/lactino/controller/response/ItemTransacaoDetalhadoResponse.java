package br.com.tbt.lactino.controller.response;


import br.com.tbt.lactino.model.ItemTransacao;
import br.com.tbt.lactino.model.enums.CategoriaProduto;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemTransacaoDetalhadoResponse(
        UUID id,
        UUID transacaoId,
        UUID produtoId,
        Integer quantidade,
        BigDecimal precoUnitario,
        CategoriaProduto categoria,
        String unidadeDeMedida
) {
    public ItemTransacaoDetalhadoResponse(ItemTransacao item) {
        this(
                item.getId(),
                item.getTransacao().getId(),
                item.getProdutoId(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getCategoria(),
                item.getUnidadeDeMedida()
        );
    }
}