package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.LoginDTO;
import br.com.tbt.lactino.controller.request.RegistroDTO;
import br.com.tbt.lactino.controller.response.LoginResponseDTO;
import br.com.tbt.lactino.controller.response.UsuarioResponse;
import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.security.TokenService;
import br.com.tbt.lactino.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UsuarioService usuarioService;
  private final TokenService tokenService;

  public AuthController(
      AuthenticationManager authenticationManager,
      UsuarioService usuarioService,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.usuarioService = usuarioService;
    this.tokenService = tokenService;
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
}
