package mz.orcamento.backend.dto.despesa;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record DespesaRequestDTO(
        @NotNull(message = "A categoria (setor) é obrigatória.")
        UUID categoriaId,

        @NotBlank(message = "A descrição da finalidade é obrigatória.")
        @Size(min = 10, max = 500)
        String descricaoFinalidade,

        @NotNull(message = "O valor da despesa é obrigatório.")
        @DecimalMin(value = "0.01", message = "O valor deve ser superior a zero.")
        BigDecimal valorDespesa,

        @NotBlank(message = "O NUIT do fornecedor é obrigatório.")
        @Pattern(regexp = "\\d{9}", message = "O NUIT deve conter exatamente 9 dígitos.")
        String nuitFornecedor,

        @NotBlank(message = "O nome do fornecedor é obrigatório.")
        String nomeFornecedor,

        @NotNull(message = "O ID do utilizador autorizador é obrigatório.")
        UUID usuarioAutorizadorId,

        String coordenadasGeograficas, // Opcional, dependendo se é obra ou serviço
        String anexoFaturaUrl,
        String anexoContratoUrl
) {}
