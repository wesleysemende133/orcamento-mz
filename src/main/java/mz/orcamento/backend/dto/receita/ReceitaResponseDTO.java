package mz.orcamento.backend.dto.receita;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReceitaResponseDTO(
        UUID id,
        String descricao,
        BigDecimal valor,
        LocalDate data,
        String nomeCategoria, // Para exibir "Salário", "Freelance", etc. sem novo fetch
        String corCategoria    // Para estilizar o badge ou ícone no Dashboard
) {}
