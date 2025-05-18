package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.Leite;
import br.com.tbt.lactino.model.enums.StatusLeiteEnum;
import br.com.tbt.lactino.model.enums.TurnoEnum;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record LeiteDTO(
        @Size(max = 100)
        String nome,

        @Size(max = 500)
        String descricao,

        @NotNull(message = "A data de obtenção é obrigatória")
        @PastOrPresent(message = "A data de obtenção não pode ser futura")
        LocalDate dataObtencao,

        @NotBlank(message = "A origem é obrigatória")
        String origem,

        @NotNull(message = "O turno é obrigatório")
        TurnoEnum turno,

        @NotNull(message = "O status é obrigatório")
        StatusLeiteEnum status,

        @Size(max = 255)
        String finalidade,

        String fornecedorId
) {

    public Leite toEntity(){
        return new Leite(nome, descricao, dataObtencao, origem, turno, status, finalidade, fornecedorId);
    }
}
