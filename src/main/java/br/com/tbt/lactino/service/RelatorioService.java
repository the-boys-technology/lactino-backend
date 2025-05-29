package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.response.RelatorioPedidoResponse;

public interface RelatorioService {
  RelatorioPedidoResponse gerarRelatorioPedido(Long transacaoId);

  byte[] gerarRelatorioPedidoPdf(Long transacaoId);
}
