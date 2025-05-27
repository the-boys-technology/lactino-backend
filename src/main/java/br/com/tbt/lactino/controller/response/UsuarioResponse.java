package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponse(
    UUID id, String nome, String email, String role, LocalDateTime criadoEm) {

  public UsuarioResponse(Usuario usuario) {
    this(
        usuario.getId(),
        usuario.getNome(),
        usuario.getEmail(),
        usuario.getRole().name(),
        usuario.getCriadoEm());
  }
}
