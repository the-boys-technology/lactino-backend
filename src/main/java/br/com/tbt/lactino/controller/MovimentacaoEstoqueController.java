package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.RegistrarMovimentacaoDTO;
import br.com.tbt.lactino.controller.response.RegistrarMovimentacaoResponse;
import br.com.tbt.lactino.service.MovimentacaoEstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoEstoqueController {
  private final MovimentacaoEstoqueService movimentacaoEstoqueService;

  @PostMapping
  public ResponseEntity<RegistrarMovimentacaoResponse> registrarMovimentacao(
      @RequestBody @Valid RegistrarMovimentacaoDTO dto) {
    RegistrarMovimentacaoResponse response =
        this.movimentacaoEstoqueService.registrarMovimentacao(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
