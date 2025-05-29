package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.response.RelatorioPedidoResponse;

import java.util.UUID;

public interface RelatorioService {

    RelatorioPedidoResponse gerarRelatorioPedido(UUID transacaoId);

    byte[] gerarRelatorioPedidoPdf(Long transacaoId);
}
