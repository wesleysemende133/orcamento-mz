package mz.orcamento.backend.dto.categoria;

import mz.orcamento.backend.model.Categoria;

import java.math.BigDecimal;
import java.util.UUID;

public record CategoriaResponseDTO(
        UUID id,
        String classificadorFuncional,
        String codigoRubrica,
        BigDecimal dotacaoInicial,
        BigDecimal saldoDisponivel
){
    public CategoriaResponseDTO(Categoria categoria) {
        this(
                categoria.getIdCategoria(),
                categoria.getClassificadorFuncional(),
                categoria.getCodigoRubrica(),
                categoria.getDotacaoInicial(),
                categoria.getSaldoDisponivel()
        );
        }
}
