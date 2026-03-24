package mz.orcamento.backend.repository;

import mz.orcamento.backend.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, UUID> {

    // Busca receitas por uma categoria específica (ex: Taxas de Lixo, Imposto Predial)
    List<Receita> findByCategoriaId(UUID categoriaId);

    // Busca receitas por período para relatórios de arrecadação mensal
    @Query("SELECT r FROM Receita r WHERE " +
            "(:mes IS NULL OR MONTH(r.dataArrecadacao) = :mes) AND " +
            "(:ano IS NULL OR YEAR(r.dataArrecadacao) = :ano)")
    List<Receita> buscarPorPeriodo(@Param("mes") Integer mes, @Param("ano") Integer ano);

    // Soma total arrecadada por categoria (Útil para gráficos no React)
    @Query("SELECT SUM(r.valorReceita) FROM Receita r WHERE r.categoria.id = :categoriaId")
    java.math.BigDecimal somarTotalPorCategoria(@Param("categoriaId") UUID categoriaId);


    ///Para Relatorio Service
    @Query("SELECT SUM(r.valorArrecado) FROM Receita r WHERE MONTH(r.dataArrecadado) = :mes AND YEAR(r.dataArrecadado) = :ano")
    BigDecimal somarReceitasPorMes(@Param("mes") int mes, @Param("ano") int ano);

}
