package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.AtualizarLaticinioDTO;
import br.com.tbt.lactino.controller.request.LaticinioDTO;
import br.com.tbt.lactino.controller.request.LaticinioFiltro;
import br.com.tbt.lactino.controller.response.LaticinioDetalhadoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface LaticinioService {

    UUID salvarLaticinio(String email, LaticinioDTO laticinioDTO);

    LaticinioDetalhadoResponse buscarLaticinio(UUID id);

    Page<LaticinioDetalhadoResponse> listarLaticinios(LaticinioFiltro filtro, Pageable pageable);

    LaticinioDetalhadoResponse atualizarLaticinio(UUID id, AtualizarLaticinioDTO laticinioDTO);

    List<LaticinioDetalhadoResponse> listarLaticiniosVencendo(String email);
}
