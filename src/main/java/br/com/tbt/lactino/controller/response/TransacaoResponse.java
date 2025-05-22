package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.controller.request.ClienteTransacaoDTO;
import br.com.tbt.lactino.model.Cliente;
import br.com.tbt.lactino.model.Transacao;
import br.com.tbt.lactino.model.enums.FormaPagamento;
import br.com.tbt.lactino.model.enums.TipoTransacao;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransacaoResponse(
        Long id,
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
    public TransacaoResponse(Transacao transacao) {
        this(
                transacao.getId(),
                transacao.getTipo(),
                transacao.getData(),
                transacao.getValorTotal(),
                transacao.getFormaPagamento(),
                transacao.getCliente().getId(),
                transacao.getFornecedorId(),
                transacao.getLeiteId(),
                transacao.getLaticinioId(),
                transacao.getDescricao()
        );
    }
}
