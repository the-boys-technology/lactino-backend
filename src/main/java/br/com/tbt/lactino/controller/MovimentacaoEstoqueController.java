package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.MovimentacaoFiltro;
import br.com.tbt.lactino.controller.request.RegistrarMovimentacaoDTO;
import br.com.tbt.lactino.controller.response.MovimentacaoEstoqueResponse;
import br.com.tbt.lactino.controller.response.PaginaDTO;
import br.com.tbt.lactino.controller.response.RegistrarMovimentacaoResponse;
import br.com.tbt.lactino.model.enums.TipoMovimentacaoEstoque;
import br.com.tbt.lactino.service.MovimentacaoEstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoEstoqueController {
  private final MovimentacaoEstoqueService movimentacaoEstoqueService;

  @GetMapping
  public ResponseEntity<PaginaDTO<MovimentacaoEstoqueResponse>> listar(
      @RequestParam(required = false) TipoMovimentacaoEstoque tipo,
      @RequestParam(required = false, name = "data_inicial")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate dataInicial,
      @RequestParam(required = false, name = "data_final")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate dataFinal,
      @RequestParam(required = false, name = "insumo_id") Long insumoId,
      Pageable pageable) {
    MovimentacaoFiltro filtro =
        MovimentacaoFiltro.builder()
            .tipo(tipo)
            .dataInicial(dataInicial)
            .dataFinal(dataFinal)
            .insumoID(insumoId)
            .build();

    Page<MovimentacaoEstoqueResponse> resposta =
        this.movimentacaoEstoqueService.listarMovimentacoes(filtro, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(new PaginaDTO<>(resposta));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MovimentacaoEstoqueResponse> detalharMovimentacao(
      @PathVariable(value = "id") String id) {
    MovimentacaoEstoqueResponse response = this.movimentacaoEstoqueService.buscarMovimentacao(id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping
  public ResponseEntity<RegistrarMovimentacaoResponse> registrarMovimentacao(
      @RequestBody @Valid RegistrarMovimentacaoDTO dto) {
    RegistrarMovimentacaoResponse response =
        this.movimentacaoEstoqueService.registrarMovimentacao(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
