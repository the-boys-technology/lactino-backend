package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.TransacaoDTO;
import br.com.tbt.lactino.controller.response.TransacaoResponse;
import java.util.List;

public interface TransacaoService {

    /**
     * Retorna a lista de transações.
     */
    List<TransacaoResponse> listarTransacoes();

    /**
     * Retorna os detalhes de uma transação específica.
     */
    TransacaoResponse buscarTransacao(Long transacaoId);

    /**
     * Registra uma nova transação de compra ou venda.
     */
    TransacaoResponse registrarTransacao(TransacaoDTO transacaoDTO);
}