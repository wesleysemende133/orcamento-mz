package mz.orcamento.backend.dto.despesa;

import mz.orcamento.backend.model.Despesa;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DespesaResponseDTO(
        UUID id,
        String descricaoFinalidade,
        BigDecimal valorDespesa,
        String nomeFornecedor,
        String nuitFornecedor,
        String faseExecucao,
        String nomeCategoria,
        String nomeAutorizador,
        LocalDateTime dataCriacao
) {
    // O construtor personalizado chama o 'this' com EXATAMENTE os 9 campos acima
    public DespesaResponseDTO(Despesa despesa) {
        this(
                despesa.getId(),
                despesa.getDescricaoFinalidade(),
                despesa.getValorDespesa(),
                despesa.getNomeFornecedor(),
                despesa.getNuitFornecedor(),
                despesa.getFaseExecucao().name(),
                despesa.getCategoria().getClassificadorFuncional(),
                despesa.getUsuarioAutorizador().getNomeUsuario(),
                despesa.getDataCriacao()
        );
    }
}
