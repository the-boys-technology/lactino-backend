package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.enums.StatusLaticinioEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record AtualizarLaticinioDTO(
        @Size(max = 100)
        String tipoProduto,

        @DecimalMin(value = "0.0", inclusive = false, message = "A quantidade deve ser maior que zero")
        BigDecimal quantidadeProduzida,

        @PastOrPresent(message = "A data de produção não pode ser futura")
        LocalDate dataProducao,

        @Size(max = 500)
        String descricao,

        StatusLaticinioEnum status,

        UUID leiteUtilizadoId
) {
}
