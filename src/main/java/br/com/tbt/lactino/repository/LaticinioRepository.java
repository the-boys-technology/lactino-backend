package br.com.tbt.lactino.repository;

import br.com.tbt.lactino.model.Laticinio;
import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.model.enums.StatusLaticinioEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LaticinioRepository extends JpaRepository<Laticinio, UUID>, JpaSpecificationExecutor<Laticinio> {

    Optional<Laticinio> findById(UUID id);

    List<Laticinio> findByUsuarioAndStatusAndDataValidadeBetween(Usuario usuario, StatusLaticinioEnum status, LocalDate inicio, LocalDate fim);

    List<Laticinio> findByStatus(StatusLaticinioEnum status);
}
