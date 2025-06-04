package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.response.ItemTransacaoDetalhadoResponse;
import br.com.tbt.lactino.model.ItemTransacao;
import br.com.tbt.lactino.repository.ItemTransacaoRepository;
import br.com.tbt.lactino.service.ItemTransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemTransacaoServiceImpl implements ItemTransacaoService {

    private final ItemTransacaoRepository itemRepository;

    @Override
    public List<ItemTransacaoDetalhadoResponse> listarItens(Optional<Long> transacaoId, Optional<Long> produtoId, Optional<String> categoria) {
        List<ItemTransacao> itens = itemRepository.findAll();

        return itens.stream()
                .filter(i -> transacaoId.map(id -> id.equals(i.getTransacao().getId())).orElse(true))
                .filter(i -> produtoId.map(id -> id.equals(i.getProdutoId())).orElse(true))
                .filter(i -> categoria.map(cat -> i.getCategoria().name().equalsIgnoreCase(cat)).orElse(true))
                .map(ItemTransacaoDetalhadoResponse::new)
                .collect(Collectors.toList());
    }
}