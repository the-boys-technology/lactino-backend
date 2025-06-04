package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.TransacaoDTO;
import br.com.tbt.lactino.controller.response.TransacaoResponse;
import br.com.tbt.lactino.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public TransacaoResponse buscarTransacao(@PathVariable UUID id) {
        return transacaoService.buscarTransacao(id);
    }

    /**
     * Endpoint para registrar uma nova transação de compra ou venda.
     *
     * @param transacaoDTO Dados da transação a ser registrada
     * @return Detalhes da transação registrada
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoResponse registrarTransacao(@RequestBody @Valid TransacaoDTO transacaoDTO) {
        return transacaoService.registrarTransacao(transacaoDTO);
    }

    /**
     * Endpoint para atualizar uma transação existente.
     *
     * @param id ID da transação
     * @param transacaoDTO Dados atualizados da transação
     * @return Detalhes da transação atualizada
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransacaoResponse atualizarTransacao(@PathVariable UUID id, @RequestBody @Valid TransacaoDTO transacaoDTO) {
        return transacaoService.atualizarTransacao(id, transacaoDTO);
    }

    /**
     * Endpoint para remover uma transação existente.
     *
     * @param id ID da transação
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerTransacao(@PathVariable UUID id) {
        transacaoService.removerTransacao(id);
    }
}