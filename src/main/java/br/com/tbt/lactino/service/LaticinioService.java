package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.AtualizarLaticinioDTO;
import br.com.tbt.lactino.controller.request.LaticinioDTO;
import br.com.tbt.lactino.controller.request.LaticinioFiltro;
import br.com.tbt.lactino.controller.response.LaticinioDetalhadoResponse;

import java.util.List;
import java.util.UUID;

public interface LaticinioService {

    UUID salvarLaticinio(String email, LaticinioDTO laticinioDTO);

    LaticinioDetalhadoResponse buscarLaticinio(UUID id);

    List<LaticinioDetalhadoResponse> listarLaticinios(String email, LaticinioFiltro filtro);

    LaticinioDetalhadoResponse atualizarLaticinio(UUID id, AtualizarLaticinioDTO laticinioDTO);

    List<LaticinioDetalhadoResponse> listarLaticiniosVencendo(String email);
}
