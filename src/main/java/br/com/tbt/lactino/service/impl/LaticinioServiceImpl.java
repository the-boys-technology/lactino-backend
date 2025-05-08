package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.LaticinioDTO;
import br.com.tbt.lactino.model.Laticinio;
import br.com.tbt.lactino.model.Leite;
import br.com.tbt.lactino.repository.LaticinioRepository;
import br.com.tbt.lactino.repository.LeiteRepository;
import br.com.tbt.lactino.service.LaticinioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LaticinioServiceImpl implements LaticinioService {

    private final LaticinioRepository laticinioRepository;
    private final LeiteRepository leiteRepository;

    public LaticinioServiceImpl(LaticinioRepository laticinioRepository, LeiteRepository leiteRepository) {
        this.laticinioRepository = laticinioRepository;
        this.leiteRepository = leiteRepository;
    }

    @Override
    public UUID salvarLaticinio(LaticinioDTO laticinioDTO) {
        Leite leite = leiteRepository.findById(laticinioDTO.leiteUtilizadoId())
                .orElseThrow(() -> new EntityNotFoundException("Leite com ID " + laticinioDTO.leiteUtilizadoId() + " não encontrado."));

        Laticinio laticinio = laticinioDTO.toEntity(leite);
        laticinioRepository.save(laticinio);
        return laticinio.getId();
    }
}
