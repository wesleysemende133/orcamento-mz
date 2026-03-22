package mz.orcamento.backend.dto.despesa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record DespesaResponseDTO(
        UUID id,
        String descricao,
        BigDecimal valor,
        LocalDate dataVencimento,
        boolean pago,
        String nomeCategoria, // Enviamos o nome para evitar que o front faça outra busca
        String corCategoria   // Útil para estilizar a lista no React (ex: cor vermelha para 'Saúde')
) {}