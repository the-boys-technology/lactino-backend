package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.RegistroDTO;
import br.com.tbt.lactino.controller.response.UsuarioResponse;
import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.repository.UsuarioRepository;
import br.com.tbt.lactino.service.EnderecoService;
import br.com.tbt.lactino.service.UsuarioService;
import br.com.tbt.lactino.service.dto.ViaCepResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final EnderecoService enderecoService;

  public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, EnderecoService enderecoService) {
    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = passwordEncoder;
      this.enderecoService = enderecoService;
  }

  @Override
  public void criarUsuario(RegistroDTO dto) {
    if (usuarioRepository.findByEmail(dto.email()) != null)
      throw new RuntimeException("Email já existe!");

    Usuario usuario = dto.toEntity();

    if (dto.cep() != null &&
            (dto.rua() == null || dto.bairro() == null || dto.cidade() == null || dto.estado() == null)){
      ViaCepResponse endereco = enderecoService.buscarEnderecorPorCep(dto.cep());
      usuario.setRua(endereco.rua());
      usuario.setBairro(endereco.bairro());
      usuario.setCidade(endereco.cidade());
      usuario.setEstado(endereco.estado());
    }

    usuario.setSenha(passwordEncoder.encode(dto.senha()));
    usuarioRepository.save(usuario);
  }

  @Override
  public UsuarioResponse verDados(Usuario usuarioAutenticado) {
    return new UsuarioResponse(usuarioAutenticado);
  }
}
