package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.AtualizarLeiteDTO;
import br.com.tbt.lactino.controller.request.LeiteDTO;
import br.com.tbt.lactino.controller.request.LeiteFiltro;
import br.com.tbt.lactino.controller.response.LeiteDetalhadoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface LeiteService {
    UUID salvarLeite(String email, LeiteDTO leiteDTO);

    LeiteDetalhadoResponse buscarLeite(UUID leiteId);

    LeiteDetalhadoResponse atualizarLeite(UUID id, AtualizarLeiteDTO leiteDTO);

    LeiteDetalhadoResponse transformarLeite(UUID leiteId);

    Page<LeiteDetalhadoResponse> listarLeitesComFiltro(LeiteFiltro filtro, Pageable pageable);

    List<LeiteDetalhadoResponse> listarLeitesVencendo(String email);

    void removerLeite(UUID leiteId);
}
