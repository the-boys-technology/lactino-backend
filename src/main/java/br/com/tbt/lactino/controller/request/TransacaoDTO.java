package br.com.tbt.lactino.controller.request;


import br.com.tbt.lactino.model.enums.FormaPagamento;
import br.com.tbt.lactino.model.enums.TipoTransacao;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TransacaoDTO(
        @NotNull TipoTransacao tipo,
        @NotNull FormaPagamento formaPagamento,
        UUID clienteId, //Pode ser nulo em compras (quando não há cliente)
        UUID fornecedorId, // Pode ser nulo em vendas (quando não há fornecedor)
        String descricao,
        @NotNull List<ItemTransacaoDTO> itens
) {
}
