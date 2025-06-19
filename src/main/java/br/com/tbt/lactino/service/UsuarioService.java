package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.AtualizarUsuarioDTO;
import br.com.tbt.lactino.controller.request.RegistroDTO;
import br.com.tbt.lactino.controller.response.UsuarioResponse;
import br.com.tbt.lactino.model.Usuario;

public interface UsuarioService {

  void criarUsuario(RegistroDTO dto);

  UsuarioResponse verDados(Usuario usuario);

  void atualizarUsuario(Usuario usuario, AtualizarUsuarioDTO usuarioDTO);
}
