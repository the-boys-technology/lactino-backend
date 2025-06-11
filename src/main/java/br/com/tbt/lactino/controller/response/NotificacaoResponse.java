package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Notificacao;
import br.com.tbt.lactino.model.enums.TipoNotificacao;

import java.time.LocalDateTime;

public record NotificacaoResponse(
        Long id,
        String titulo,
        String mensagem,
        TipoNotificacao tipo,
        boolean lida,
        LocalDateTime criadaEm
) {

    public NotificacaoResponse(Notificacao notificacao) {
        this(notificacao.getId(), notificacao.getTitulo(), notificacao.getMensagem(),
                notificacao.getTipoNotificacao(), notificacao.isLida(), notificacao.getCriadaEm());
    }

}
