package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.RegistrarMovimentacaoDTO;
import br.com.tbt.lactino.controller.response.MovimentacaoEstoqueResponse;
import br.com.tbt.lactino.controller.response.RegistrarMovimentacaoResponse;
import br.com.tbt.lactino.service.MovimentacaoEstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoEstoqueController {
  private final MovimentacaoEstoqueService movimentacaoEstoqueService;

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
