package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Transacao;
import br.com.tbt.lactino.model.enums.FormaPagamento;
import br.com.tbt.lactino.model.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ClienteTransacaoResponse(
        Long id,
        TipoTransacao tipo,
        LocalDateTime data,
        BigDecimal valorTotal,
        FormaPagamento formaPagamento,
        UUID fornecedorId,
        String descricao
) {
    public ClienteTransacaoResponse(Transacao transacao) {
        this(
                transacao.getId(),
                transacao.getTipo(),
                transacao.getData(),
                transacao.getValorTotal(),
                transacao.getFormaPagamento(),
                transacao.getFornecedor().getId(),
                transacao.getDescricao()
        );
    }
}
