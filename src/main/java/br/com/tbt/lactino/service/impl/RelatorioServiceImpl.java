package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.response.ItemRelatorioResponse;
import br.com.tbt.lactino.controller.response.RelatorioPedidoResponse;
import br.com.tbt.lactino.model.Cliente;
import br.com.tbt.lactino.model.Fornecedor;
import br.com.tbt.lactino.model.ItemTransacao;
import br.com.tbt.lactino.model.Transacao;
import br.com.tbt.lactino.model.enums.CategoriaProduto;
import br.com.tbt.lactino.repository.*;
import br.com.tbt.lactino.service.RelatorioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RelatorioServiceImpl implements RelatorioService {

    private final TransacaoRepository transacaoRepository;
    private final ItemTransacaoRepository itemTransacaoRepository;
    private final LeiteRepository leiteRepository;
    private final LaticinioRepository laticinioRepository;
    private final InsumoRepository insumoRepository;

    public RelatorioServiceImpl(
            TransacaoRepository transacaoRepository,
            ItemTransacaoRepository itemTransacaoRepository,
            LeiteRepository leiteRepository,
            LaticinioRepository laticinioRepository,
            InsumoRepository insumoRepository
    ) {
        this.transacaoRepository = transacaoRepository;
        this.itemTransacaoRepository = itemTransacaoRepository;
        this.leiteRepository = leiteRepository;
        this.laticinioRepository = laticinioRepository;
        this.insumoRepository = insumoRepository;
    }

    @Override
    public RelatorioPedidoResponse gerarRelatorioPedido(Long transacaoId) {
        Transacao transacao = transacaoRepository.findById(transacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));

        Cliente cliente = transacao.getCliente();
        Fornecedor fornecedor = transacao.getFornecedor();

        List<ItemTransacao> itens = itemTransacaoRepository.findByTransacaoId(transacaoId);

        List<ItemRelatorioResponse> itensRelatorio = itens.stream()
                .map(item -> {
                    String nomeProduto = buscarNomeProduto(item.getCategoria(), item.getProdutoId());

                    BigDecimal subtotal = item.getPrecoUnitario()
                            .multiply(BigDecimal.valueOf(item.getQuantidade()));

                    return new ItemRelatorioResponse(
                            nomeProduto,
                            item.getCategoria().name(),
                            item.getQuantidade(),
                            item.getPrecoUnitario(),
                            subtotal
                    );
                })
                .collect(Collectors.toList());

        return new RelatorioPedidoResponse(
                transacao.getId(),
                transacao.getData(),
                cliente != null ? cliente.getNome() : "N/A",
                cliente != null ? cliente.getEmail() : "N/A",
                cliente != null ? cliente.getLocalizacao() : "N/A",
                fornecedor != null ? fornecedor.getNome() : "N/A",
                fornecedor != null ? fornecedor.getEmail() : "N/A",
                fornecedor != null ? fornecedor.getLocalizacao() : "N/A",
                transacao.getFormaPagamento().name(),
                transacao.getValorTotal(),
                transacao.getDescricao(),
                itensRelatorio
        );
    }

    private String buscarNomeProduto(CategoriaProduto categoria, UUID produtoId) {
        return switch (categoria) {
            case LEITE -> leiteRepository.findById(produtoId)
                    .orElseThrow(() -> new EntityNotFoundException("Leite não encontrado"))
                    .getNome();

            case LATICINIO -> laticinioRepository.findById(produtoId)
                    .orElseThrow(() -> new EntityNotFoundException("Laticínio não encontrado"))
                    .getTipoProduto();

            case INSUMO -> insumoRepository.findById(produtoId)
                    .orElseThrow(() -> new EntityNotFoundException("Insumo não encontrado"))
                    .getNome();
        };
    }
}
