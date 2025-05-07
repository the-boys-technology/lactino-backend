package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Leite;
import br.com.tbt.lactino.model.enums.StatusLeiteEnum;
import br.com.tbt.lactino.model.enums.TurnoEnum;

import java.time.LocalDate;
import java.util.UUID;

public record LeiteDetalhadoResponse(
        UUID id,
        String nome,
        String descricao,
        LocalDate dataObtencao,
        LocalDate dataValidade,
        String origem,
        TurnoEnum turno,
        StatusLeiteEnum status,
        String finalidade,
        String fornecedorId
) {
    public LeiteDetalhadoResponse(Leite leite) {
        this(
                leite.getId(),
                leite.getNome(),
                leite.getDescricao(),
                leite.getDataObtencao(),
                leite.getDataValidade(),
                leite.getOrigem(),
                leite.getTurno(),
                leite.getStatus(),
                leite.getFinalidade(),
                leite.getFornecedor()
        );
    }
}
