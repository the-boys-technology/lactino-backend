package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.RegistrarMovimentacaoDTO;
import br.com.tbt.lactino.controller.response.RegistrarMovimentacaoResponse;

public interface MovimentacaoEstoqueService {

  RegistrarMovimentacaoResponse registrarMovimentacao(RegistrarMovimentacaoDTO dto);
}
