package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.MovimentacaoFiltro;
import br.com.tbt.lactino.controller.request.RegistrarMovimentacaoDTO;
import br.com.tbt.lactino.controller.response.MovimentacaoEstoqueResponse;
import br.com.tbt.lactino.controller.response.RegistrarMovimentacaoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovimentacaoEstoqueService {

  RegistrarMovimentacaoResponse registrarMovimentacao(RegistrarMovimentacaoDTO dto);

  MovimentacaoEstoqueResponse buscarMovimentacao(String id);

  Page<MovimentacaoEstoqueResponse> listarMovimentacoes(
      MovimentacaoFiltro filtro, Pageable pageable);
}
