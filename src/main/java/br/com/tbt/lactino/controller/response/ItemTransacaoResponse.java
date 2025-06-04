package br.com.tbt.lactino.controller.response;


import br.com.tbt.lactino.model.ItemTransacao;
import br.com.tbt.lactino.model.enums.CategoriaProduto;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemTransacaoResponse(
        UUID id,
        UUID produtoId,
        Integer quantidade,
        BigDecimal precoUnitario,
        CategoriaProduto categoria,
        String unidadeDeMedida
) {
    public ItemTransacaoResponse(ItemTransacao item) {
        this(
                item.getId(),
                item.getProdutoId(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getCategoria(),
                item.getUnidadeDeMedida()
        );
    }
}