package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.response.TransacaoResponse;
import br.com.tbt.lactino.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    /**
     * Endpoint para listar todas as transações.
     *
     * @return Lista de transações
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransacaoResponse> listarTransacoes() {
        return transacaoService.listarTransacoes();
    }

    /**
     * Endpoint para buscar uma transação específica por ID.
     *
     * @param id ID da transação
     * @return Detalhes da transação
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransacaoResponse buscarTransacao(@PathVariable Long id) {
        return transacaoService.buscarTransacao(id);
    }
}