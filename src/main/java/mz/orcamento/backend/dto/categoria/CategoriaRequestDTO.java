package mz.orcamento.backend.dto.categoria;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Contrato de entrada para a criação de rubricas orçamentais.
 * Permite a segmentação do orçamento geral em unidades funcionais
 * conforme o classificador económico vigente.
 */
public record CategoriaRequestDTO(
        @NotNull(message = "O ID do orçamento pai é obrigatório.")
        UUID orcamentoId,

        @NotBlank(message = "O classificador funcional é obrigatório (ex: Educação, Saúde).")
        String classificadorFuncional,

        @NotBlank(message = "O código da rubrica é obrigatório (ex: 311.01).")
        @Pattern(regexp = "^[0-9.]+$", message = "O código da rubrica deve conter apenas números e pontos.")
        String codigoRubrica,

        @NotNull(message = "A dotação inicial é obrigatória.")
        @DecimalMin(value = "0.00", message = "A dotação inicial não pode ser negativa.")
        BigDecimal dotacaoInicial,

        @Size(max = 1000, message = "A justificativa de impacto não deve exceder 1000 caracteres.")
        String justificativaImpacto
) {}
