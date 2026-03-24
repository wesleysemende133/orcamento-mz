package mz.orcamento.backend.repository;

import mz.orcamento.backend.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, UUID> {

    // Busca o orçamento ativo do município (Ex: Orçamento de 2024)
    @Query("SELECT o FROM Orcamento o WHERE o.anoFiscal = :ano AND o.ativo = true")
    Optional<Orcamento> findByAnoFiscalAtivo(Integer ano);

    // Método para verificar se existe orçamento configurado para o ID enviado no DTO
    boolean existsById(UUID id);
}