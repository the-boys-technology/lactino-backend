package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.RegistroDTO;
import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.repository.UsuarioRepository;
import br.com.tbt.lactino.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void criarUsuario(RegistroDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) throw new RuntimeException("Email já existe!");
        Usuario usuario = dto.toEntity();
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuarioRepository.save(usuario);
    }
}
