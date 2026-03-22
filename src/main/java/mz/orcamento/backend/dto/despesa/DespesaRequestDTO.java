package mz.orcamento.backend.dto.despesa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record DespesaRequestDTO(
        String descricao,
        BigDecimal valor,
        LocalDate dataVencimento,
        boolean pago,
        UUID categoriaId,
        UUID usuarioId
) {}
