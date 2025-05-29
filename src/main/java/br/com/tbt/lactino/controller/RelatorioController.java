package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.response.RelatorioPedidoResponse;
import br.com.tbt.lactino.service.RelatorioService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<RelatorioPedidoResponse> verificaRelatorioPedido(@PathVariable Long id) {
        RelatorioPedidoResponse response = relatorioService.gerarRelatorioPedido(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

  @GetMapping(value = "/pedidos/imprimir/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> baixarRelatorioPdf(@PathVariable Long id) {
        byte[] relatorio = this.relatorioService.gerarRelatorioPedidoPdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "relatorio-pedido-" + id + ".pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(relatorio);
    }
}
