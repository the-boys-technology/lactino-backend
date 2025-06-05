package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.MudarSenhaDTO;
import br.com.tbt.lactino.controller.request.ResetarSenhaDTO;
import br.com.tbt.lactino.controller.request.SolicitarResetSenhaDTO;
import br.com.tbt.lactino.model.Usuario;

public interface SenhaService {

  void solicitarResetSenha(SolicitarResetSenhaDTO dto);

  void resetarSenha(ResetarSenhaDTO dto);

  void mudarSenha(Usuario usuario, MudarSenhaDTO dto);
}
