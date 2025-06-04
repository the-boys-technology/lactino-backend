package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.ResetarSenhaDTO;
import br.com.tbt.lactino.controller.request.SolicitarResetSenhaDTO;
import br.com.tbt.lactino.model.ResetarSenha;
import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.repository.ResetarSenhaRepository;
import br.com.tbt.lactino.repository.UsuarioRepository;
import br.com.tbt.lactino.service.EnviarEmailService;
import br.com.tbt.lactino.service.SenhaService;
import br.com.tbt.lactino.service.dto.EnviarEmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SenhaServiceImpl implements SenhaService {
  private static final int TAMANHO_CODIGO = 6;
  private static final int VALIDADE_CODIGO_MINUTOS = 10;
  private final ResetarSenhaRepository resetarSenhaRepository;
  private final EnviarEmailService enviarEmailService;
  private final UsuarioRepository usuarioRepository;

  @Override
  public void solicitarResetSenha(SolicitarResetSenhaDTO dto) {
    if (this.usuarioRepository.existsByEmail(dto.email())) {
      throw new RuntimeException("Não foi encontrado nenhum usuário com o email encontrado");
    }

    ResetarSenha resetarSenha =
        ResetarSenha.builder()
            .email(dto.email())
            .codigo(gerarCodigo())
            .dataExpiracao(LocalDateTime.now().plusMinutes(VALIDADE_CODIGO_MINUTOS))
            .build();

    this.resetarSenhaRepository.save(resetarSenha);

    EnviarEmailDTO emailDTO =
        EnviarEmailDTO.builder()
            .to(dto.email())
            .from("minha.aplicação@email.com")
            .subject("Solicitação de Reset de Senha")
            .body("Seu códido para resetar a senha é " + resetarSenha.getCodigo())
            .build();

    this.enviarEmailService.enviarEmail(emailDTO);
  }

  @Override
  @Transactional
  public void resetarSenha(ResetarSenhaDTO dto) {

    // Código comentado até a implementação do serviço de email
    /*
    ResetarSenha resetarSenha =
        this.resetarSenhaRepository
            .findByEmailAndCodigo(dto.email(), dto.codigo())
            .orElseThrow(
                () -> new RuntimeException("A solicitação de reset de senha não foi encontrada"));

    if (resetarSenha.getDataExpiracao().isBefore(LocalDateTime.now())) {
      throw new RuntimeException("O código de reset de senha está expirado");
    }
    */

    Usuario usuario = this.usuarioRepository.findByEmail(dto.email());

    if (usuario == null) {
      throw new RuntimeException("Nenhum usuário foi encontrado com o email " + dto.email());
    }

    usuario.setSenha(dto.novaSenha());
    this.usuarioRepository.save(usuario);
  }

  private static String gerarCodigo() {
    SecureRandom random = new SecureRandom();
    StringBuilder code = new StringBuilder(TAMANHO_CODIGO);

    for (int i = 0; i < TAMANHO_CODIGO; i++) {
      boolean isNumber = random.nextBoolean();

      char character;
      if (isNumber) {
        character = (char) (random.nextInt(10) + 48);
      } else {
        character = (char) (random.nextInt(26) + 65);
      }
      code.append(character);
    }

    return code.toString();
  }
}
