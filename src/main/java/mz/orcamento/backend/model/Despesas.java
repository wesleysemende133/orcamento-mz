package mz.orcamento.backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "despesas")
@Getter
@Setter
public class Despesas {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria; // A que sector esta despesa pertence (ex: Saúde)

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricaoFinalidade;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal valorDespesa;

    @Column(nullable = false, length = 9)
    private String nuitFornecedor;

    @Column(nullable = false)
    private String nomeFornecedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_autorizador_id", nullable = false)
    private User usuarioAutorizador; // Quem aprovou a despesa (Auditoria)

    private String anexoFaturaUrl;
    private String anexoContratoUrl;
    private String coordenadasGeograficas; // Para obras públicas

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FaseExecucao faseExecucao;

    public enum FaseExecucao {
        CABIMENTACAO, // Reserva do valor
        LIQUIDACAO,   // Verificação da fatura/serviço
        PAGAMENTO     // Saída do dinheiro
    }
}