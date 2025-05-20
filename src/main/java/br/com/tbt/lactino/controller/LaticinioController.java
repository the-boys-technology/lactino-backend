package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.request.AtualizarLaticinioDTO;
import br.com.tbt.lactino.controller.request.LaticinioDTO;
import br.com.tbt.lactino.controller.request.LaticinioFiltro;
import br.com.tbt.lactino.controller.response.LaticinioDetalhadoResponse;
import br.com.tbt.lactino.security.TokenService;
import br.com.tbt.lactino.service.LaticinioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/laticinios")
public class LaticinioController {

    private final LaticinioService laticinioService;
    private final TokenService tokenService;

    public LaticinioController(LaticinioService laticinioService, TokenService tokenService) {
        this.laticinioService = laticinioService;
        this.tokenService = tokenService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID salvarLaticinio(HttpServletRequest request, @RequestBody @Valid LaticinioDTO laticinioDTO) {
        String email = tokenService.validarToken(request.getHeader("Authorization").substring(7));
        return laticinioService.salvarLaticinio(email, laticinioDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LaticinioDetalhadoResponse buscarLaticinio(@PathVariable UUID id) {
        return laticinioService.buscarLaticinio(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<LaticinioDetalhadoResponse> listarLaticinios(HttpServletRequest request, @ModelAttribute LaticinioFiltro filtro) {
        String email = tokenService.validarToken(request.getHeader("Authorization").substring(7));
        return laticinioService.listarLaticinios(email, filtro);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LaticinioDetalhadoResponse atualizarLaticinio(@PathVariable UUID id, @RequestBody @Valid AtualizarLaticinioDTO laticinioDTO) {
        return laticinioService.atualizarLaticinio(id, laticinioDTO);
    }

    @GetMapping("/vencendo")
    @ResponseStatus(HttpStatus.OK)
    public List<LaticinioDetalhadoResponse> listarLaticiniosVencendo(HttpServletRequest request) {
        String email = tokenService.validarToken(request.getHeader("Authorization").substring(7));
        return laticinioService.listarLaticiniosVencendo(email);
    }
}
