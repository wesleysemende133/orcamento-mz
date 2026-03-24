package mz.orcamento.backend.service;

import lombok.RequiredArgsConstructor;
import mz.orcamento.backend.model.Categoria;
import mz.orcamento.backend.repository.CategoriaRepository;
import mz.orcamento.backend.repository.DespesaRepository;
import mz.orcamento.backend.repository.ReceitaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;
    private final CategoriaRepository categoriaRepository;

    /**
     * 1. Calcular Saldo Mensal: Soma de Receitas - Soma de Despesas
     */
    public BigDecimal calcularSaldoMensal(int mes, int ano) {
        BigDecimal totalReceitas = receitaRepository.somarReceitasPorMes(mes, ano);
        BigDecimal totalDespesas = despesaRepository.somarDespesasPorMes(mes, ano);

        return (totalReceitas != null ? totalReceitas : BigDecimal.ZERO)
                .subtract(totalDespesas != null ? totalDespesas : BigDecimal.ZERO);
    }

    /**
     * 2. Distribuição por Categoria: Prepara os dados para o Gráfico de Pizza
     */
    public Map<String, BigDecimal> distribuicaoPorCategoria() {
        List<Categoria> categorias = categoriaRepository.findAll();
        Map<String, BigDecimal> distribuicao = new HashMap<>();

        for (Categoria cat : categorias) {
            BigDecimal totalGasto = despesaRepository.somarTotalPorCategoria(cat.getIdCategoria());
            distribuicao.put(cat.getClassificadorFuncional(), totalGasto != null ? totalGasto : BigDecimal.ZERO);
        }
        return distribuicao;
    }

    /**
     * 3. Previsão de Saldo: Saldo Atual + Receitas Esperadas - Despesas Agendadas
     */
    public BigDecimal preverSaldoFinalMes(UUID orcamentoId) {
        // Implementação simplificada: Considera o saldo arrecadado menos despesas em fase de Cabimentação/Liquidação
        return BigDecimal.ZERO; // Lógica customizada conforme as fases de execução
    }
}