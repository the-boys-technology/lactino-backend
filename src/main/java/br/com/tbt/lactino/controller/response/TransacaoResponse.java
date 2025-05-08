package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Transacao;
import br.com.tbt.lactino.model.enums.FormaPagamento;
import br.com.tbt.lactino.model.enums.TipoTransacao;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponse(
        Long id,
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
    public TransacaoResponse(Transacao transacao) {
        this(
                transacao.getId(),
                transacao.getTipo(),
                transacao.getData(),
                transacao.getValorTotal(),
                transacao.getFormaPagamento(),
                transacao.getClienteId(),
                transacao.getFornecedorId(),
                transacao.getLeiteId(),
                transacao.getLaticinioId(),
                transacao.getDescricao()
        );
    }
}
