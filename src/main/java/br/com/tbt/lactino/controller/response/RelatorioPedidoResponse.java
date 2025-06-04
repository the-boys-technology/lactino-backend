package br.com.tbt.lactino.controller.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record RelatorioPedidoResponse(
        java.util.UUID transacaoId,
        LocalDateTime data,
        String clienteNome,
        String clienteEmail,
        String clienteLocalizacao,
        String fornecedorNome,
        String fornecedorEmail,
        String fornecedorLocalizacao,
        String formaPagamento,
        BigDecimal valorTotal,
        String descricao,
        List<ItemRelatorioResponse> itens
) {
}