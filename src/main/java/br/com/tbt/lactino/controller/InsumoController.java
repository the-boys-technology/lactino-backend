package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.CadastrarInsumoDTO;
import br.com.tbt.lactino.controller.request.InsumoFiltro;
import br.com.tbt.lactino.controller.response.CadastrarInsumoResponse;
import br.com.tbt.lactino.controller.response.InsumoResponse;
import br.com.tbt.lactino.controller.response.PaginaDTO;
import br.com.tbt.lactino.model.enums.CategoriaInsumoEnum;
import br.com.tbt.lactino.model.enums.StatusInsumoEnum;
import br.com.tbt.lactino.service.InsumoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping
  public ResponseEntity<PaginaDTO<InsumoResponse>> listarInsumos(
      @RequestParam(required = false) String nome,
      @RequestParam(required = false) CategoriaInsumoEnum categoria,
      @RequestParam(required = false) StatusInsumoEnum status,
      @RequestParam(required = false, name = "em_baixo_estoque") Boolean emBaixoEstoque,
      Pageable pageable) {

    InsumoFiltro filtro =
        InsumoFiltro.builder()
            .nome(nome)
            .categoria(categoria)
            .status(status)
            .emBaixoEstoque(emBaixoEstoque)
            .build();

    Page<InsumoResponse> response = this.insumoService.listarInsumos(filtro, pageable);
    return ResponseEntity.status(HttpStatus.OK).body(new PaginaDTO<>(response));
  }

  @GetMapping("/{id}")
  public ResponseEntity<InsumoResponse> detalharInsumo(@PathVariable(value = "id") String id) {
    InsumoResponse response = this.insumoService.buscarPorId(id);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarInsumo(@PathVariable(value = "id") String id) {
    this.insumoService.deletarInsumo(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
