package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.response.NotificacaoResponse;
import br.com.tbt.lactino.model.Notificacao;
import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.repository.NotificacaoRepository;
import br.com.tbt.lactino.service.NotificacaoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacaoServiceImpl implements NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    public NotificacoaServiceImpl(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    @Override
    public List<NotificacaoResponse> listarTodas(Usuario usuario) {
        return notificacaoRepository.findByUsuarioOrderByCriadaEmDesc(usuario)
                .stream()
                .map(NotificacaoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificacaoResponse> listarNaoLidas(Usuario usuario) {
        return notificacaoRepository.findByUsuarioAndLidaFalse(usuario)
                .stream()
                .map(NotificacaoResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public void marcarComoLida(Long id, Usuario usuario) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada"));

        if (notificacao.getUsuario().equals(usuario)) {
            throw new RuntimeException("Acesso não autorizado");
        }

        notificacao.setLida(true);
        notificacaoRepository.save(notificacao);
    }
}
