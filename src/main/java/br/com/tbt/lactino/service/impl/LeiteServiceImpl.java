package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.LeiteDTO;
import br.com.tbt.lactino.controller.response.LeiteDetalhadoResponse;
import br.com.tbt.lactino.model.Leite;
import br.com.tbt.lactino.repository.LeiteRepository;
import br.com.tbt.lactino.service.LeiteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LeiteServiceImpl implements LeiteService {

    private final LeiteRepository leiteRepository;

    public LeiteServiceImpl(LeiteRepository leiteRepository) {
        this.leiteRepository = leiteRepository;
    }

    @Override
    public UUID salvarLeite(LeiteDTO leiteDTO) {
        Leite leite = leiteDTO.toEntity();
        leiteRepository.save(leite);
        return leite.getId();
    }

    @Override
    public LeiteDetalhadoResponse buscarLeite(UUID leiteId) {
        return leiteRepository.findById(leiteId)
                .map(LeiteDetalhadoResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("Leite com ID " + leiteId + " não encontrado."));
    }
}
