package br.com.tbt.lactino.controller.response;


import br.com.tbt.lactino.model.ItemTransacao;
import br.com.tbt.lactino.model.enums.CategoriaProduto;

import java.math.BigDecimal;

public record ItemTransacaoResponse(
        Long id,
        Long transacaoId,
        Long produtoId,
        Integer quantidade,
        BigDecimal precoUnitario,
        CategoriaProduto categoria
) {
    public ItemTransacaoResponse(ItemTransacao item) {
        this(
                item.getId(),
                item.getTransacaoId(),
                item.getProdutoId(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getCategoria()
        );
    }
}