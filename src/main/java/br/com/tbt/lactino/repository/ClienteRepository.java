package br.com.tbt.lactino.repository;

import br.com.tbt.lactino.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID>{
}
