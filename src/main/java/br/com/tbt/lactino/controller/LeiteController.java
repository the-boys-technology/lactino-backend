package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.AtualizarLeiteDTO;
import br.com.tbt.lactino.controller.request.LeiteDTO;
import br.com.tbt.lactino.controller.request.LeiteFiltro;
import br.com.tbt.lactino.controller.response.LeiteDetalhadoResponse;
import br.com.tbt.lactino.security.TokenService;
import br.com.tbt.lactino.service.LeiteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leites")
public class LeiteController {

    private final LeiteService leiteService;
    private final TokenService tokenService;

    public LeiteController(LeiteService leiteService, TokenService tokenService) {
        this.leiteService = leiteService;
        this.tokenService = tokenService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID salvarLeite(HttpServletRequest request, @RequestBody @Valid LeiteDTO leiteDTO) {
        String email = tokenService.validarToken(request.getHeader("Authorization").substring(7));
        return leiteService.salvarLeite(email, leiteDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LeiteDetalhadoResponse buscarLeite(@PathVariable UUID id) {
        return leiteService.buscarLeite(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LeiteDetalhadoResponse atualizarLeite(@PathVariable UUID id, @RequestBody @Valid AtualizarLeiteDTO leiteDTO) {
        return leiteService.atualizarLeite(id, leiteDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LeiteDetalhadoResponse transformarLeite(@PathVariable UUID id) {
        return leiteService.transformarLeite(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LeiteDetalhadoResponse> listarLeites(HttpServletRequest request, @ModelAttribute LeiteFiltro filtro) {
        String email = tokenService.validarToken(request.getHeader("Authorization").substring(7));
        return leiteService.listarLeitesComFiltro(email, filtro);
    }

    @GetMapping("/vencendo")
    @ResponseStatus(HttpStatus.OK)
    public List<LeiteDetalhadoResponse> listarLeitesVencendo(HttpServletRequest request) {
        String email = tokenService.validarToken(request.getHeader("Authorization").substring(7));
        return leiteService.listarLeitesVencendo(email);
    }
}
