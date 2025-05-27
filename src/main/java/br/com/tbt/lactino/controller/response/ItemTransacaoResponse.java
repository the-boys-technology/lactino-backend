package br.com.tbt.lactino.controller.response;


import br.com.tbt.lactino.model.ItemTransacao;
import br.com.tbt.lactino.model.enums.CategoriaProduto;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemTransacaoResponse(
        Long id,
        Long transacaoId,
        UUID produtoId,
        Integer quantidade,
        BigDecimal precoUnitario,
        CategoriaProduto categoria
) {
    public ItemTransacaoResponse(ItemTransacao item) {
        this(
                item.getId(),
                item.getTransacao().getId(),
                item.getProdutoId(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getCategoria()
        );
    }
}