package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.LaticinioDTO;
import br.com.tbt.lactino.controller.response.LaticinioDetalhadoResponse;
import br.com.tbt.lactino.service.LaticinioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/laticinios")
public class LaticinioController {

    private final LaticinioService laticinioService;

    public LaticinioController(LaticinioService laticinioService) {
        this.laticinioService = laticinioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID salvarLaticinio(@RequestBody @Valid LaticinioDTO laticinioDTO) {
        return laticinioService.salvarLaticinio(laticinioDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LaticinioDetalhadoResponse buscarLaticinio(@PathVariable UUID id) {
        return laticinioService.buscarLaticinio(id);
    }
}
