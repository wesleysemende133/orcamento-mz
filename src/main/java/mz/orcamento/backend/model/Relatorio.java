package mz.orcamento.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Relatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id", nullable = false)
    private Municipio municipio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRelatorio tipoRelatorio;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime dataGeracao;

    /* JSONB permite guardar o estado do orçamento no momento exato
       para que, mesmo que os dados mudem no futuro, o relatório
       mensal permaneça fiel ao que foi fechado naquele dia.
    */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String dadosAgregadosJson;

    @Column(nullable = false, unique = true)
    private String hashAssinaturaDigital; // Identificador de integridade do documento

    public enum TipoRelatorio {
        BALANCETE_MENSAL,
        EXECUCAO_TRIMESTRAL,
        CONTA_GERENCIA_ANUAL
    }
}
