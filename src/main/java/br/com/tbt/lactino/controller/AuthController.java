package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.*;
import br.com.tbt.lactino.controller.response.LoginResponseDTO;
import br.com.tbt.lactino.controller.response.UsuarioResponse;
import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.security.TokenService;
import br.com.tbt.lactino.service.SenhaService;
import br.com.tbt.lactino.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UsuarioService usuarioService;
  private final TokenService tokenService;
  private final SenhaService senhaService;

  public AuthController(
      AuthenticationManager authenticationManager,
      UsuarioService usuarioService,
      TokenService tokenService,
      SenhaService senhaService) {
    this.authenticationManager = authenticationManager;
    this.usuarioService = usuarioService;
    this.tokenService = tokenService;
    this.senhaService = senhaService;
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public LoginResponseDTO login(@RequestBody @Valid LoginDTO dto) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    String token = tokenService.criarToken((Usuario) auth.getPrincipal());
    return new LoginResponseDTO(token);
  }

  @PostMapping("/registrar")
  @ResponseStatus(HttpStatus.CREATED)
  public void registrar(@RequestBody @Valid RegistroDTO dto) {
    usuarioService.criarUsuario(dto);
  }

  @GetMapping("/ver-dados")
  public ResponseEntity<UsuarioResponse> verDados(
      @AuthenticationPrincipal Usuario usuarioAutenticado) {
    UsuarioResponse response = this.usuarioService.verDados(usuarioAutenticado);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/solicitar-redefinicao-senha")
  public ResponseEntity<Void> solicitarRedefinicaoSenha(
      @RequestBody @Valid SolicitarResetSenhaDTO dto) {
    this.senhaService.solicitarResetSenha(dto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("/redefinir-senha")
  public ResponseEntity<Void> redefinirSenha(@RequestBody @Valid ResetarSenhaDTO dto) {
    this.senhaService.resetarSenha(dto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping("/mudar-senha")
  public ResponseEntity<Void> redefinirSenha(
      @AuthenticationPrincipal Usuario usuario, @RequestBody @Valid MudarSenhaDTO dto) {
    this.senhaService.mudarSenha(usuario, dto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping(path = "/editar-dados")
  public ResponseEntity<Void> editarUsuario(@AuthenticationPrincipal Usuario usuario,
                                            @RequestBody @Valid AtualizarUsuarioDTO dto) throws IOException {
    usuarioService.atualizarUsuario(usuario, dto);
    return ResponseEntity.noContent().build();
  }
}
