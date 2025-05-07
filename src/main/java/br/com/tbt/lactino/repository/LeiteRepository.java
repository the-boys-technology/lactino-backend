package br.com.tbt.lactino.repository;

import br.com.tbt.lactino.model.Leite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface LeiteRepository extends JpaRepository<Leite, UUID> , JpaSpecificationExecutor<Leite> {

    Optional<Leite> findById(UUID id);
}
