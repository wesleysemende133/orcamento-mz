package mz.orcamento.backend.dto.categoria;

import java.util.UUID;

public record CategoriaResponseDTO(
        UUID id,
        String nome,
        String descricao,
        String cor // Útil para categorias em sistemas de orçamento/gráficos
){}
