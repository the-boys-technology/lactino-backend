package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.CadastrarClienteDTO;
import br.com.tbt.lactino.controller.response.ClienteResponse;
import br.com.tbt.lactino.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("cliente")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrarCliente(
            @RequestBody @Valid CadastrarClienteDTO dto) {
        ClienteResponse response = this.clienteService.cadastrarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarClientePorId(@PathVariable(value = "id") UUID id) {
        ClienteResponse response = this.clienteService.buscarClientePorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping()
    public ResponseEntity <List<ClienteResponse>> buscarClientes() {
        List<ClienteResponse> response = this.clienteService.buscarClientes();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ClienteResponse atualizarCliente(@PathVariable UUID id, @RequestBody @Valid CadastrarClienteDTO dto) {
        return clienteService.atualizarCliente(id, dto);
    }
}
