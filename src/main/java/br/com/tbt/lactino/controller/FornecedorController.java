package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.CadastrarFornecedorDTO;
import br.com.tbt.lactino.controller.response.FornecedorResponse;
import br.com.tbt.lactino.service.FornecedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("fornecedor")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<FornecedorResponse> cadastrarFornecedor(
            @RequestBody @Valid CadastrarFornecedorDTO dto) {
        FornecedorResponse response = this.fornecedorService.cadastrarFornecedor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponse> buscarFornecedorPorId(@PathVariable(value = "id") UUID id) {
        FornecedorResponse response = this.fornecedorService.buscarFornecedorPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping()
    public ResponseEntity <List<FornecedorResponse>> buscarFornecedores() {
        List<FornecedorResponse> response = this.fornecedorService.buscarFornecedores();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public FornecedorResponse atualizarFornecedor(@PathVariable UUID id, @RequestBody @Valid CadastrarFornecedorDTO dto) {
        return fornecedorService.atualizarFornecedor(id, dto);
    }
}
