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

    // Alterado de findByOrcamentoId para findByOrcamentoIdOrcamento
    // O Spring agora vai procurar 'idOrcamento' dentro da classe Orcamento
    List<Receita> findByOrcamentoIdOrcamento(UUID orcamentoId);

    @Query("SELECT r FROM Receita r WHERE " +
            "(:mes IS NULL OR MONTH(r.dataArrecadado) = :mes) AND " +
            "(:ano IS NULL OR YEAR(r.dataArrecadado) = :ano)")
    List<Receita> buscarPorPeriodo(@Param("mes") Integer mes, @Param("ano") Integer ano);

    @Query("SELECT SUM(r.valorArrecado) FROM Receita r WHERE r.tipoReceita = :tipo")
    BigDecimal somarTotalPorTipo(@Param("tipo") Receita.TipoReceita tipo);

    @Query("SELECT SUM(r.valorArrecado) FROM Receita r WHERE MONTH(r.dataArrecadado) = :mes AND YEAR(r.dataArrecadado) = :ano")
    BigDecimal somarReceitasPorMes(@Param("mes") int mes, @Param("ano") int ano);
}
