package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.AtualizarLeiteDTO;
import br.com.tbt.lactino.controller.request.LeiteDTO;
import br.com.tbt.lactino.controller.response.LeiteDetalhadoResponse;
import br.com.tbt.lactino.model.Leite;
import br.com.tbt.lactino.model.enums.StatusLeiteEnum;
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

    @Override
    public LeiteDetalhadoResponse atualizarLeite(UUID id, AtualizarLeiteDTO leiteDTO) {
        Leite leite = leiteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Leite com ID " + id + " não encontrado."));

        if (leiteDTO.nome() != null) leite.setNome(leiteDTO.nome());
        if (leiteDTO.descricao() != null) leite.setDescricao(leiteDTO.descricao());
        if (leiteDTO.dataObtencao() != null) {
            leite.setDataObtencao(leiteDTO.dataObtencao());
            leite.setDataValidade(leiteDTO.dataObtencao().plusDays(7));
        }
        if (leiteDTO.origem() != null) leite.setOrigem(leiteDTO.origem());
        if (leiteDTO.turno() != null) leite.setTurno(leiteDTO.turno());
        if (leiteDTO.status() != null) leite.setStatus(leiteDTO.status());
        if (leiteDTO.finalidade() != null) leite.setFinalidade(leiteDTO.finalidade());

        leiteRepository.save(leite);
        return new LeiteDetalhadoResponse(leite);
    }

    @Override
    public LeiteDetalhadoResponse transformarLeite(UUID leiteId) {
        Leite leite = leiteRepository.findById(leiteId)
                .orElseThrow(() -> new EntityNotFoundException("Leite com ID " + leiteId + " não encontrado."));

        leite.setStatus(StatusLeiteEnum.UTILIZADO);
        leiteRepository.save(leite);
        return new LeiteDetalhadoResponse(leite);
    }
}
