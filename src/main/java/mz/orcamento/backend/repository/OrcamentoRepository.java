package mz.orcamento.backend.repository;

import mz.orcamento.backend.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, UUID> {

    // 1. Corrigido para 'anoEconomico'
    // 2. Corrigido para filtrar pelo Status 'EM_EXECUCAO' (que seria o seu 'ativo')
    @Query("SELECT o FROM Orcamento o WHERE o.anoEconomico = :ano AND o.status = mz.orcamento.backend.model.Orcamento.StatusOrcamento.EM_EXECUCAO")
    Optional<Orcamento> findByAnoAtivo(@Param("ano") Integer ano);

    // Se preferir uma busca mais flexível por status:
    List<Orcamento> findByAnoEconomicoAndStatus(Integer anoEconomico, Orcamento.StatusOrcamento status);
}