package br.com.tbt.lactino.repository;


import br.com.tbt.lactino.model.ItemTransacao;
import br.com.tbt.lactino.model.enums.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemTransacaoRepository extends JpaRepository<ItemTransacao, UUID> {

    List<ItemTransacao> findByTransacaoId(Long transacaoId);

    List<ItemTransacao> findByProdutoId(UUID produtoId);

    List<ItemTransacao> findByCategoria(CategoriaProduto categoria);
}