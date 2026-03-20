package mz.orcamento.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idOrcamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id", nullable = false)
    private Municipio municipio;

    @Column(nullable = false)
    private Integer anoEconomico;

    @Column(precision = 19, scale = 1)
    private BigDecimal valorTotalPrevisto;

    @Column(precision = 19, scale = 1)
    private BigDecimal valorTotalArrecadado;

    @Column(precision = 19, scale = 1)
    private BigDecimal ValorTotalExecutado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOrcamento status;

    private String documentoAprovacaoUrl;

    public enum StatusOrcamento{
        EM_ELABORACAO,
        APROVADO_ASSEMBLEIA,
        EM_EXECUCAO,
        ENCERRADO
    }
}
