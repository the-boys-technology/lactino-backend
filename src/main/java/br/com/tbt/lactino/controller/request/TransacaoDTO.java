package br.com.tbt.lactino.controller.request;


import br.com.tbt.lactino.model.Cliente;
import br.com.tbt.lactino.model.enums.FormaPagamento;
import br.com.tbt.lactino.model.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransacaoDTO(
        TipoTransacao tipo,
        LocalDateTime data,
        BigDecimal valorTotal,
        FormaPagamento formaPagamento,
        UUID clienteId,
        Long fornecedorId,
        Long leiteId,
        Long laticinioId,
        String descricao
) {
}
