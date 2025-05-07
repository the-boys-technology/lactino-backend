package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.CadastrarInsumoDTO;
import br.com.tbt.lactino.controller.response.CadastrarInsumoResponse;

public interface InsumoService {
  CadastrarInsumoResponse cadastrarInsumo(CadastrarInsumoDTO dto);
}
