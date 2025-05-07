package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.LeiteDTO;
import br.com.tbt.lactino.controller.response.LeiteDetalhadoResponse;
import br.com.tbt.lactino.service.LeiteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/leites")
public class LeiteController {

    private final LeiteService leiteService;

    public LeiteController(LeiteService leiteService) {
        this.leiteService = leiteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarLeite(@RequestBody @Valid LeiteDTO leiteDTO) {
        leiteService.salvarLeite(leiteDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LeiteDetalhadoResponse buscarLeite(@PathVariable UUID id) {
        return leiteService.buscarLeite(id);
    }
}
