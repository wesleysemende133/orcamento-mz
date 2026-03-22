package mz.orcamento.backend.dto.Orcamento;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Projeção de saída para a visualização do orçamento.
 * Inclui indicadores de desempenho financeiro (Execução e Arrecadação)
 * calculados pela camada de serviço.
 */
public record OrcamentoResponseDTO(
        UUID id,
        Integer anoEconomico,
        BigDecimal valorTotalPrevisto,
        BigDecimal valorTotalArrecadado,
        BigDecimal valorTotalExecutado,
        BigDecimal saldoRemanescente, // Calculado: Previsto - Executado
        String status,
        String nomeMunicipio,
        String documentoAprovacaoUrl
) {}