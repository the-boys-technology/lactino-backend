package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.response.NotificacaoResponse;
import br.com.tbt.lactino.model.Usuario;

import java.util.List;

public interface NotificacaoService {

    List<NotificacaoResponse> listarTodas(Usuario usuario);

    List<NotificacaoResponse> listarNaoLidas(Usuario usuario);

    void marcarComoLida(Long id, Usuario usuario);
}
