package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.model.enums.StatusLeiteEnum;
import br.com.tbt.lactino.model.enums.TurnoEnum;

public record LeiteFiltro(
        StatusLeiteEnum status,
        String origem,
        TurnoEnum turno,
        Usuario usuario
) {}