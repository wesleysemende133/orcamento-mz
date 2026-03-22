package mz.orcamento.backend.dto.relatorio;


import mz.orcamento.backend.dto.despesa.DespesaResponseDTO;
import mz.orcamento.backend.dto.receita.ReceitaResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record RelatorioResponseDTO(
        BigDecimal saldoTotal,
        BigDecimal totalReceitas,
        BigDecimal totalDespesas,
        BigDecimal percentualEconomia,
        // Agrupamento por categoria: Nome da Categoria -> Valor Gasto
        Map<String, BigDecimal> gastosPorCategoria,
        // Lista de transações recentes para o painel principal
        List<DespesaResponseDTO> ultimasDespesas,
        List<ReceitaResponseDTO> ultimasReceitas
) {}
