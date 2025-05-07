package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.enums.StatusLeiteEnum;
import br.com.tbt.lactino.model.enums.TurnoEnum;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AtualizarLeiteDTO(
    @Size(max = 100)
    String nome,

    @Size(max = 500)
    String descricao,

    @PastOrPresent(message = "A data de obtenção não pode ser futura")
    LocalDate dataObtencao,

    String origem,

    TurnoEnum turno,

    StatusLeiteEnum status,

    @Size(max = 255)
    String finalidade
){
}
