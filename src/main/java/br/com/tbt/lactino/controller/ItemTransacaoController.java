package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.response.ItemTransacaoDetalhadoResponse;
import br.com.tbt.lactino.service.ItemTransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/itens-transacoes")
@RequiredArgsConstructor
public class ItemTransacaoController {

    private final ItemTransacaoService itemService;

    /**
     * Retorna todos os itens vinculados às transações, com filtros opcionais.
     */
    @GetMapping
    public List<ItemTransacaoDetalhadoResponse> listarItens(
            @RequestParam(required = false) Long transacaoId,
            @RequestParam(required = false) Long produtoId,
            @RequestParam(required = false) String categoria
    ) {
        return itemService.listarItens(
                Optional.ofNullable(transacaoId),
                Optional.ofNullable(produtoId),
                Optional.ofNullable(categoria)
        );
    }
}