package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.enums.StatusLaticinioEnum;

import java.util.UUID;

public record LaticinioFiltro(
        String tipo,
        StatusLaticinioEnum status,
        UUID leiteUtilizadoId
) {
}
