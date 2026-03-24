package mz.orcamento.backend.dto.receita;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import mz.orcamento.backend.model.Receita.TipoReceita;
import java.math.BigDecimal;
import java.util.UUID;

public record ReceitaRequestDTO(
        @NotBlank(message = "A descrição da arrecadação é obrigatória")
        String descricao,

        @NotNull(message = "O valor da receita não pode ser nulo")
        @DecimalMin(value = "0.01", message = "O valor arrecadado deve ser superior a zero")
        BigDecimal valor,

        @NotNull(message = "O ID do orçamento municipal é obrigatório")
        UUID orcamentoId,

        @NotNull(message = "O tipo de receita (FCA, Impostos, etc) deve ser selecionado")
        TipoReceita tipoReceita,

        @NotBlank(message = "O link ou referência do comprovativo de depósito é obrigatório")
        String comprovativoDepositoUrl
) {}