package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.ItemTransacaoDTO;
import br.com.tbt.lactino.controller.request.TransacaoDTO;
import br.com.tbt.lactino.controller.response.TransacaoResponse;
import br.com.tbt.lactino.model.Cliente;
import br.com.tbt.lactino.model.Fornecedor;
import br.com.tbt.lactino.model.ItemTransacao;
import br.com.tbt.lactino.model.Transacao;
import br.com.tbt.lactino.repository.*;
import br.com.tbt.lactino.service.TransacaoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ItemTransacaoRepository itemTransacaoRepository;
    private final ClienteRepository clienteRepository;
    private final FornecedorRepository fornecedorRepository;
    private final LeiteRepository leiteRepository;
    private final LaticinioRepository laticinioRepository;
    private final InsumoRepository insumoRepository;

    @Override
    public List<TransacaoResponse> listarTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        return transacoes.stream()
                .map(TransacaoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public TransacaoResponse buscarTransacao(UUID transacaoId) {
        Transacao transacao = transacaoRepository.findById(transacaoId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        return new TransacaoResponse(transacao);
    }

    @Override
    public TransacaoResponse registrarTransacao(TransacaoDTO transacaoDTO) {
        Cliente cliente = null;
        if (transacaoDTO.clienteId() != null) {
            cliente = clienteRepository.findById(transacaoDTO.clienteId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        }

        Fornecedor fornecedor = null;
        if (transacaoDTO.fornecedorId() != null) {
            fornecedor = fornecedorRepository.findById(transacaoDTO.fornecedorId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));
        }

        BigDecimal valorTotal = transacaoDTO.itens().stream()
                .map(item -> item.precoUnitario().multiply(BigDecimal.valueOf(item.quantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Cria a transação
        Transacao transacao = Transacao.builder()
                .tipo(transacaoDTO.tipo())
                .data(LocalDateTime.now())
                .valorTotal(valorTotal)
                .formaPagamento(transacaoDTO.formaPagamento())
                .cliente(cliente)
                .fornecedor(fornecedor)
                .descricao(transacaoDTO.descricao())
                .build();

        // Salva a transação primeiro para gerar o ID
        Transacao transacaoSalva = transacaoRepository.save(transacao);

        // Cria e salva os itens
        List<ItemTransacao> itens = transacaoDTO.itens().stream()
                .map(itemDTO -> criarItemTransacao(itemDTO, transacaoSalva))
                .collect(Collectors.toList());

        itemTransacaoRepository.saveAll(itens);
        return new TransacaoResponse(transacaoSalva);
    }

    @Override
    public TransacaoResponse atualizarTransacao(UUID transacaoId, TransacaoDTO transacaoDTO) {
        Transacao transacaoExistente = transacaoRepository.findById(transacaoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada"));

        Cliente cliente = null;
        if (transacaoDTO.clienteId() != null) {
            cliente = clienteRepository.findById(transacaoDTO.clienteId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        }

        Fornecedor fornecedor = null;
        if (transacaoDTO.fornecedorId() != null) {
            fornecedor = fornecedorRepository.findById(transacaoDTO.fornecedorId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));
        }

        BigDecimal valorTotal = transacaoDTO.itens().stream()
                .map(item -> item.precoUnitario().multiply(BigDecimal.valueOf(item.quantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        transacaoExistente.setTipo(transacaoDTO.tipo());
        transacaoExistente.setData(LocalDateTime.now());
        transacaoExistente.setValorTotal(valorTotal);
        transacaoExistente.setFormaPagamento(transacaoDTO.formaPagamento());
        transacaoExistente.setCliente(cliente);
        transacaoExistente.setFornecedor(fornecedor);
        transacaoExistente.setDescricao(transacaoDTO.descricao());

        Transacao transacaoAtualizada = transacaoRepository.save(transacaoExistente);

        // Remove itens antigos
        List<ItemTransacao> itensAntigos = itemTransacaoRepository.findByTransacaoId(transacaoId);
        itemTransacaoRepository.deleteAll(itensAntigos);

        // Adiciona itens novos
        List<ItemTransacao> itensNovos = transacaoDTO.itens().stream()
                .map(itemDTO -> criarItemTransacao(itemDTO, transacaoAtualizada))
                .collect(Collectors.toList());

        itemTransacaoRepository.saveAll(itensNovos);
        return new TransacaoResponse(transacaoAtualizada);
    }

    @Override
    public void removerTransacao(UUID transacaoId) {
        Transacao transacao = transacaoRepository.findById(transacaoId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        transacaoRepository.delete(transacao);
    }

    private ItemTransacao criarItemTransacao(ItemTransacaoDTO dto, Transacao transacao) {
        validarProduto(dto.categoria(), dto.produtoId());

        return ItemTransacao.builder()
                .transacao(transacao)
                .produtoId(dto.produtoId())
                .categoria(dto.categoria())
                .quantidade(dto.quantidade())
                .precoUnitario(dto.precoUnitario())
                .build();
    }

    // 🔥 Validação se o produto existe
    private void validarProduto(br.com.tbt.lactino.model.enums.CategoriaProduto categoria, UUID produtoId) {
        switch (categoria) {
            case LEITE -> leiteRepository.findById(produtoId)
                    .orElseThrow(() -> new EntityNotFoundException("Leite não encontrado com ID: " + produtoId));
            case LATICINIO -> laticinioRepository.findById(produtoId)
                    .orElseThrow(() -> new EntityNotFoundException("Laticínio não encontrado com ID: " + produtoId));
            case INSUMO -> insumoRepository.findById(produtoId)
                    .orElseThrow(() -> new EntityNotFoundException("Insumo não encontrado com ID: " + produtoId));
        }
    }

}