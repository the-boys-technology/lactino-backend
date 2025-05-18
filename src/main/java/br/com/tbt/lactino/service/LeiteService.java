package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.AtualizarLeiteDTO;
import br.com.tbt.lactino.controller.request.LeiteDTO;
import br.com.tbt.lactino.controller.request.LeiteFiltro;
import br.com.tbt.lactino.controller.response.LeiteDetalhadoResponse;

import java.util.List;
import java.util.UUID;

public interface LeiteService {
    UUID salvarLeite(LeiteDTO leiteDTO);

    LeiteDetalhadoResponse buscarLeite(UUID leiteId);

    LeiteDetalhadoResponse atualizarLeite(UUID id, AtualizarLeiteDTO leiteDTO);

    LeiteDetalhadoResponse transformarLeite(UUID leiteId);

    List<LeiteDetalhadoResponse> listarLeitesComFiltro(LeiteFiltro filtro);

    List<LeiteDetalhadoResponse> listarLeitesVencendo();
}
