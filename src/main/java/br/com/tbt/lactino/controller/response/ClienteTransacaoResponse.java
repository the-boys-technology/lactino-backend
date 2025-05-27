package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Transacao;
import br.com.tbt.lactino.model.enums.FormaPagamento;
import br.com.tbt.lactino.model.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ClienteTransacaoResponse(
        Long id,
        TipoTransacao tipo,
        LocalDateTime data,
        BigDecimal valorTotal,
        FormaPagamento formaPagamento,
        Long fornecedorId,
        Long leiteId,
        Long laticinioId,
        String descricao
) {
    public ClienteTransacaoResponse(Transacao transacao) {
        this(
                transacao.getId(),
                transacao.getTipo(),
                transacao.getData(),
                transacao.getValorTotal(),
                transacao.getFormaPagamento(),
                transacao.getFornecedorId(),
                transacao.getLeiteId(),
                transacao.getLaticinioId(),
                transacao.getDescricao()
        );
    }
}
