package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Transacao;
import br.com.tbt.lactino.model.enums.FormaPagamento;
import br.com.tbt.lactino.model.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TransacaoResponse(
        UUID id,
        TipoTransacao tipo,
        LocalDateTime data,
        BigDecimal valorTotal,
        FormaPagamento formaPagamento,
        TransacaoClienteResponse cliente,
        TransacaoFornecedorResponse fornecedor,
        String descricao,
        List<ItemTransacaoResponse> itens
) {
    public TransacaoResponse(Transacao transacao) {
        this(
                transacao.getId(),
                transacao.getTipo(),
                transacao.getData(),
                transacao.getValorTotal(),
                transacao.getFormaPagamento(),
                transacao.getCliente() != null ?    new TransacaoClienteResponse(transacao.getCliente()) : null,
                transacao.getFornecedor() != null ? new TransacaoFornecedorResponse(transacao.getFornecedor()) : null,
                transacao.getDescricao(),
                transacao.getItens().stream()
                        .map(ItemTransacaoResponse::new)
                        .toList()
        );
    }
}
