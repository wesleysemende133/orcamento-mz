package mz.orcamento.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
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

    private enum TipoReceita{
        RECEITA_PROPRIA_IMPOSTOS,
        RECEITA_PROPRIA_TAXAS,
        FCA_TRANSFERENCA_ESTADO, //fundo de compecacao autarquica
        DOACOES
    }
}
