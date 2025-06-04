package br.com.tbt.lactino.repository;

import br.com.tbt.lactino.model.ResetarSenha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResetarSenhaRepository extends JpaRepository<ResetarSenha, UUID> {
  Optional<ResetarSenha> findByEmailAndCodigo(String email, String codigo);
}
