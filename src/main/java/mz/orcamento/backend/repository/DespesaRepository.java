package mz.orcamento.backend.repository;

import mz.orcamento.backend.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, UUID> {

    // Busca despesas por uma categoria específica (ex: todas as despesas da Saúde)
    List<Despesa> findByCategoriaId(UUID categoriaId);

    // Busca despesas pelo NUIT do fornecedor para auditoria de contratos
    List<Despesa> findByNuitFornecedor(String nuitFornecedor);

    // Query customizada para o histórico com filtros (Mês e Ano)
    @Query("SELECT d FROM Despesa d WHERE " +
            "(:mes IS NULL OR MONTH(d.dataCriacao) = :mes) AND " +
            "(:ano IS NULL OR YEAR(d.dataCriacao) = :ano) AND " +
            "(:categoriaId IS NULL OR d.categoria.id = :categoriaId)")
    List<Despesa> buscarComFiltros(@Param("mes") Integer mes,
                                   @Param("ano") Integer ano,
                                   @Param("categoriaId") UUID categoriaId);
}
