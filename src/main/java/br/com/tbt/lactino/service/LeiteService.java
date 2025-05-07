package br.com.tbt.lactino.service;

import br.com.tbt.lactino.controller.request.LeiteDTO;
import br.com.tbt.lactino.controller.response.LeiteDetalhadoResponse;

import java.util.UUID;

public interface LeiteService {
    void salvarLeite(LeiteDTO leiteDTO);

    LeiteDetalhadoResponse buscarLeite(UUID leiteId);
}
