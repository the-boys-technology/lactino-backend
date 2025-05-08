package br.com.tbt.lactino.controller.response;

import br.com.tbt.lactino.model.Laticinio;
import br.com.tbt.lactino.model.enums.StatusLaticinioEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LaticinioDetalhadoResponse (
        UUID id,
        String tipoProduto,
        BigDecimal quantidadeProduzida,
        LocalDate dataProducao,
        LocalDate dataValidade,
        UUID leiteUtilizadoId,
        StatusLaticinioEnum status,
        String descricao
) {
    public LaticinioDetalhadoResponse(Laticinio laticinio) {
        this(
                laticinio.getId(),
                laticinio.getTipoProduto(),
                laticinio.getQuantidadeProduzida(),
                laticinio.getDataProducao(),
                laticinio.getDataValidade(),
                laticinio.getLeiteUtilizado().getId(),
                laticinio.getStatus(),
                laticinio.getDescricao()
        );
    }
}
