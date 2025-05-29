package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.response.RelatorioPedidoResponse;
import br.com.tbt.lactino.service.RelatorioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<RelatorioPedidoResponse> verificaRelatorioPedido(@PathVariable UUID id) {
        RelatorioPedidoResponse response = relatorioService.gerarRelatorioPedido(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
