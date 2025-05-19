package br.com.tbt.lactino.repository;

import br.com.tbt.lactino.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Usuario findByEmail(String email);
}
