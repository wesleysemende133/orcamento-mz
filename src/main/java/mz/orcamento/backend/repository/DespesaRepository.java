package mz.orcamento.backend.repository;

import mz.orcamento.backend.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, UUID> {

    // CORRIGIDO: de findByCategoriaId para findByCategoriaIdCategoria
    List<Despesa> findByCategoriaIdCategoria(UUID categoriaId);

    List<Despesa> findByNuitFornecedor(String nuitFornecedor);

    @Query("SELECT d FROM Despesa d WHERE " +
            "(:mes IS NULL OR MONTH(d.dataCriacao) = :mes) AND " +
            "(:ano IS NULL OR YEAR(d.dataCriacao) = :ano) AND " +
            "(:categoriaId IS NULL OR d.categoria.idCategoria = :categoriaId)") // CORRIGIDO: .id para .idCategoria
    List<Despesa> buscarComFiltros(@Param("mes") Integer mes,
                                   @Param("ano") Integer ano,
                                   @Param("categoriaId") UUID categoriaId);

    @Query("SELECT SUM(d.valorDespesa) FROM Despesa d WHERE MONTH(d.dataCriacao) = :mes AND YEAR(d.dataCriacao) = :ano")
    BigDecimal somarDespesasPorMes(@Param("mes") int mes, @Param("ano") int ano);

    @Query("SELECT SUM(d.valorDespesa) FROM Despesa d WHERE d.categoria.idCategoria = :idCategoria")
    BigDecimal somarTotalPorCategoria(@Param("idCategoria") UUID idCategoria);
}

