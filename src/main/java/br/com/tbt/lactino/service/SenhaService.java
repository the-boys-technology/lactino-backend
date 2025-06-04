package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.ResetarSenhaDTO;
import br.com.tbt.lactino.controller.request.SolicitarResetSenhaDTO;

public interface SenhaService {

  void solicitarResetSenha(SolicitarResetSenhaDTO dto);

  void resetarSenha(ResetarSenhaDTO dto);
}
