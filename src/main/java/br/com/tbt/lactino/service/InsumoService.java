package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.CadastrarInsumoDTO;
import br.com.tbt.lactino.controller.response.CadastrarInsumoResponse;
import br.com.tbt.lactino.controller.response.InsumoResponse;
import br.com.tbt.lactino.model.Insumo;
import br.com.tbt.lactino.model.enums.TipoMovimentacaoEstoque;

import java.util.UUID;

public interface InsumoService {
  CadastrarInsumoResponse cadastrarInsumo(CadastrarInsumoDTO dto);

  InsumoResponse buscarPorId(String id);

  void deletarInsumo(String id);

  Insumo buscarInsumo(UUID id);

  void alterarQuantidade(UUID id, Double quantidadeAlterar, TipoMovimentacaoEstoque tipoAlteracao);
}
