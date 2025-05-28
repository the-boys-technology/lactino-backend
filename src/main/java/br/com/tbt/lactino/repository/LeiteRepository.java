package br.com.tbt.lactino.repository;

import br.com.tbt.lactino.model.Leite;
import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.model.enums.StatusLeiteEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LeiteRepository extends JpaRepository<Leite, UUID> , JpaSpecificationExecutor<Leite> {

    Optional<Leite> findById(UUID id);

    List<Leite> findByUsuarioAndStatusAndDataValidadeLessThanEqual(Usuario usuario, StatusLeiteEnum status, LocalDate dataLimite);

    List<Leite> findByStatus(StatusLeiteEnum status);

}
