package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.LeiteDTO;
import br.com.tbt.lactino.model.Leite;
import br.com.tbt.lactino.repository.LeiteRepository;
import br.com.tbt.lactino.service.LeiteService;
import org.springframework.stereotype.Service;

@Service
public class LeiteServiceImpl implements LeiteService {

    private final LeiteRepository leiteRepository;

    public LeiteServiceImpl(LeiteRepository leiteRepository) {
        this.leiteRepository = leiteRepository;
    }

    @Override
    public void salvarLeite(LeiteDTO leiteDTO) {
        Leite leite = leiteDTO.toEntity();
        leiteRepository.save(leite);
    }
}
