package br.com.tbt.lactino.repository;

import br.com.tbt.lactino.model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface MovimentacaoEstoqueRepository
    extends JpaRepository<MovimentacaoEstoque, UUID>,
        JpaSpecificationExecutor<MovimentacaoEstoque> {}
