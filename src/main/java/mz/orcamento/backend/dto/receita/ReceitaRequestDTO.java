package mz.orcamento.backend.dto.receita;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReceitaRequestDTO(
        String descricao,
        BigDecimal valor,
        LocalDate data,
        UUID categoriaId,
        UUID usuarioId
) {}