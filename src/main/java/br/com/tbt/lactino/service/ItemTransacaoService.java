package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.response.ItemTransacaoDetalhadoResponse;

import java.util.List;
import java.util.Optional;

public interface ItemTransacaoService {
    List<ItemTransacaoDetalhadoResponse> listarItens(Optional<Long> transacaoId, Optional<Long> produtoId, Optional<String> categoria);
}