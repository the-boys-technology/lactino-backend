package br.com.tbt.lactino.repository;

import br.com.tbt.lactino.model.Leite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeiteRepository extends JpaRepository<Leite, UUID> {
}
