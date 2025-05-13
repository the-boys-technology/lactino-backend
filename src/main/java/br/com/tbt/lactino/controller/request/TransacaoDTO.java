package br.com.tbt.lactino.controller.request;


import br.com.tbt.lactino.model.enums.FormaPagamento;
import br.com.tbt.lactino.model.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoDTO(
        TipoTransacao tipo,
        LocalDateTime data,
        BigDecimal valorTotal,
        FormaPagamento formaPagamento,
        Long clienteId,
        Long fornecedorId,
        Long leiteId,
        Long laticinioId,
        String descricao
) {
}
