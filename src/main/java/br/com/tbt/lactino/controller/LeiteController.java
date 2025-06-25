package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.AtualizarLeiteDTO;
import br.com.tbt.lactino.controller.request.LeiteDTO;
import br.com.tbt.lactino.controller.request.LeiteFiltro;
import br.com.tbt.lactino.controller.response.LeiteDetalhadoResponse;
import br.com.tbt.lactino.controller.response.PaginaDTO;
import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.model.enums.StatusLeiteEnum;
import br.com.tbt.lactino.model.enums.TurnoEnum;
import br.com.tbt.lactino.security.TokenService;
import br.com.tbt.lactino.service.LeiteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public PaginaDTO<LeiteDetalhadoResponse> listarLeites(
            @RequestParam(required = false) StatusLeiteEnum status,
            @RequestParam(required = false) String origem,
            @RequestParam(required = false) TurnoEnum turno,
            Pageable pageable,
            @AuthenticationPrincipal Usuario usuario) {

        LeiteFiltro filtro = new LeiteFiltro(status, origem, turno, usuario);
        Page<LeiteDetalhadoResponse> response = leiteService.listarLeitesComFiltro(filtro, pageable);
        return new PaginaDTO<>(response);
    }

    @GetMapping("/vencendo")
    @ResponseStatus(HttpStatus.OK)
    public List<LeiteDetalhadoResponse> listarLeitesVencendo(HttpServletRequest request) {
        String email = tokenService.validarToken(request.getHeader("Authorization").substring(7));
        return leiteService.listarLeitesVencendo(email);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removerLeite(@PathVariable UUID id) {
        this.leiteService.removerLeite(id);
    }

}
