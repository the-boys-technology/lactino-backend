package br.com.tbt.lactino.repository;


import br.com.tbt.lactino.model.ItemTransacao;
import br.com.tbt.lactino.model.enums.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemTransacaoRepository extends JpaRepository<ItemTransacao, Long> {

    List<ItemTransacao> findByTransacaoId(Long transacaoId);

    List<ItemTransacao> findByProdutoId(Long produtoId);

    List<ItemTransacao> findByCategoria(CategoriaProduto categoria);
}