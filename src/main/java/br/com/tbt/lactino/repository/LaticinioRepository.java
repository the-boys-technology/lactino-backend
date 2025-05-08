package br.com.tbt.lactino.repository;

import br.com.tbt.lactino.model.Laticinio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LaticinioRepository extends JpaRepository<Laticinio, UUID> {

    Optional<Laticinio> findById(UUID id);
}
