package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Transacao;
import br.com.tbt.lactino.model.enums.FormaPagamento;
import br.com.tbt.lactino.model.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record FornecedorTransacaoResponse(
        UUID id,
        TipoTransacao tipo,
        LocalDateTime data,
        BigDecimal valorTotal,
        FormaPagamento formaPagamento,
        UUID clienteId,
        String descricao) {

    public FornecedorTransacaoResponse(Transacao transacao){
        this(
                transacao.getId(),
                transacao.getTipo(),
                transacao.getData(),
                transacao.getValorTotal(),
                transacao.getFormaPagamento(),
                transacao.getCliente() != null ? transacao.getCliente().getId() : null,
                transacao.getDescricao()
        );
    }
}
