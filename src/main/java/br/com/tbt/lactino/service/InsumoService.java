package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.CadastrarInsumoDTO;
import br.com.tbt.lactino.controller.response.CadastrarInsumoResponse;
import br.com.tbt.lactino.controller.response.InsumoResponse;

public interface InsumoService {
  CadastrarInsumoResponse cadastrarInsumo(CadastrarInsumoDTO dto);

  InsumoResponse buscarPorId(String id);
}
