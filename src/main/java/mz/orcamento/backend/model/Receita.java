package mz.orcamento.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mz.orcamento.backend.dto.receita.ReceitaRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idReceita;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id", nullable = false)
    private Orcamento orcamento;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal valorArrecado;

    @Column(nullable = false)
    private LocalDate dataArrecadado;

    @Column(nullable = false)
    private String comprovativoDepositoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private  TipoReceita tipoReceita;

    public enum TipoReceita{
        RECEITA_PROPRIA_IMPOSTOS,
        RECEITA_PROPRIA_TAXAS,
        FCA_TRANSFERENCA_ESTADO, //fundo de compecacao autarquica
        DOACOES
    }
    // Adicione à sua classe Receita.java
    public Receita(ReceitaRequestDTO dto, Orcamento orcamento) {
        this.orcamento = orcamento;
        // Mapeamos os nomes do DTO para os nomes do SEU modelo
        this.valorArrecado = dto.valor();
        this.dataArrecadado = java.time.LocalDate.now(); // Data automática do sistema
        this.comprovativoDepositoUrl = dto.comprovativoDepositoUrl();
        this.tipoReceita = dto.tipoReceita();

        // O idReceita não é passado aqui pois o Hibernate gera o UUID automaticamente
    }
}
