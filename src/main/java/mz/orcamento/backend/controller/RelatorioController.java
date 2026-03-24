package mz.orcamento.backend.controller;

import lombok.RequiredArgsConstructor;
import mz.orcamento.backend.service.RelatorioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite que o seu React consuma os dados
public class RelatorioController {

    private final RelatorioService relatorioService;

    /**
     * Endpoint para os Cards do Dashboard: Saldo do Mês
     * Ex: GET /api/relatorios/saldo-mensal?mes=3&ano=2024
     */
    @GetMapping("/saldo-mensal")
    public ResponseEntity<BigDecimal> getSaldoMensal(
            @RequestParam int mes,
            @RequestParam int ano) {
        return ResponseEntity.ok(relatorioService.calcularSaldoMensal(mes, ano));
    }

    /**
     * Endpoint para o Gráfico de Pizza: Gastos por Rubrica
     * Ex: GET /api/relatorios/distribuicao-categorias
     */
    @GetMapping("/distribuicao-categorias")
    public ResponseEntity<Map<String, BigDecimal>> getDistribuicaoPorCategoria() {
        return ResponseEntity.ok(relatorioService.distribuicaoPorCategoria());
    }

    /**
     * Endpoint para Alertas de Tesouraria: Previsão de Saldo Final
     * Ex: GET /api/relatorios/previsao/UUID-DO-ORCAMENTO
     */
    @GetMapping("/previsao/{orcamentoId}")
    public ResponseEntity<BigDecimal> getPrevisaoSaldo(@PathVariable UUID orcamentoId) {
        return ResponseEntity.ok(relatorioService.preverSaldoFinalMes(orcamentoId));
    }
}