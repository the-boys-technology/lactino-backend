package br.com.tbt.lactino.controller;

import br.com.tbt.lactino.controller.response.NotificacaoResponse;
import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.service.NotificacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @GetMapping
    public ResponseEntity<List<NotificacaoResponse>> listarTodasNotificações(
            @AuthenticationPrincipal Usuario usuario) {
        List<NotificacaoResponse> response = notificacaoService.listarTodas(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/nao-lidas")
    public ResponseEntity<List<NotificacaoResponse>> listarNotificacoesNaoLidas(
            @AuthenticationPrincipal Usuario usuario) {
        List<NotificacaoResponse> response = notificacaoService.listarNaoLidas(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}/ler")
    public ResponseEntity<Void> marcarComoLida(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario) {
        notificacaoService.marcarComoLida(id, usuario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
