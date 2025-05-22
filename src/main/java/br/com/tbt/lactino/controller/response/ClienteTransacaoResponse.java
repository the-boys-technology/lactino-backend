package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.controller.request.ClienteTransacaoDTO;
import br.com.tbt.lactino.model.Cliente;
import br.com.tbt.lactino.model.Transacao;
import br.com.tbt.lactino.model.enums.FormaPagamento;
import br.com.tbt.lactino.model.enums.TipoTransacao;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
