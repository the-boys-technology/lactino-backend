package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.CadastrarInsumoDTO;
import br.com.tbt.lactino.controller.response.CadastrarInsumoResponse;
import br.com.tbt.lactino.service.InsumoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("insumos")
@RequiredArgsConstructor
public class InsumoController {
  private final InsumoService insumoService;

  @PostMapping
  public ResponseEntity<CadastrarInsumoResponse> cadastrarInsumo(
      @RequestBody @Valid CadastrarInsumoDTO dto) {
    CadastrarInsumoResponse response = this.insumoService.cadastrarInsumo(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
