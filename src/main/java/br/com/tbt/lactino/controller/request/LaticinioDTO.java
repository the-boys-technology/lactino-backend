package br.com.tbt.lactino.controller.request;

import br.com.tbt.lactino.model.Laticinio;
import br.com.tbt.lactino.model.Leite;
import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.model.enums.StatusLaticinioEnum;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LaticinioDTO(
        @NotBlank(message = "O tipo do produto é obrigatório")
        String tipoProduto,

        @NotNull(message = "A quantidade produzida é obrigatória")
        @DecimalMin(value = "0.0", inclusive = false, message = "A quantidade deve ser maior que zero")
        BigDecimal quantidadeProduzida,

        @NotNull(message = "A data de produção é obrigatória")
        @PastOrPresent(message = "A data de produção não pode ser futura")
        LocalDate dataProducao,

        @NotNull(message = "O ID do leite utilizado é obrigatório")
        UUID leiteUtilizadoId,

        @NotNull(message = "O status é obrigatório")
        StatusLaticinioEnum status,

        @Size(max = 500, message = "A descrição pode ter no máximo 500 caracteres")
        String descricao
) {
    public Laticinio toEntity(Leite leite, Usuario usuario) {
        return new Laticinio(tipoProduto, quantidadeProduzida, dataProducao, descricao, status, leite, usuario);
    }
}
