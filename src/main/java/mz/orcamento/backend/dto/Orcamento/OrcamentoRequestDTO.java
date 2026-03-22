package mz.orcamento.backend.dto.Orcamento;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Contrato de entrada para a submissão do orçamento anual municipal.
 * Garante que os valores previstos sejam positivos e vinculados a um ano fiscal válido.
 */
public record OrcamentoRequestDTO(
        @NotNull(message = "O ano económico é obrigatório.")
        @Min(value = 2024, message = "O ano económico deve ser igual ou superior a 2024.")
        @Max(value = 2100, message = "Ano económico fora do intervalo permitido.")
        Integer anoEconomico,

        @NotNull(message = "O valor total previsto é obrigatório.")
        @DecimalMin(value = "0.01", message = "O valor previsto deve ser superior a zero.")
        BigDecimal valorTotalPrevisto,

        @NotNull(message = "O ID do município é obrigatório para vincular o orçamento.")
        UUID municipioId,

        @NotBlank(message = "A URL do documento de aprovação (Assembleia Municipal) é obrigatória.")
        String documentoAprovacaoUrl
) {}
