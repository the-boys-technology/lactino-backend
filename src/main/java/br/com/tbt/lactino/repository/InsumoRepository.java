package br.com.tbt.lactino.repository;

import br.com.tbt.lactino.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InsumoRepository extends JpaRepository<Insumo, UUID> {}
