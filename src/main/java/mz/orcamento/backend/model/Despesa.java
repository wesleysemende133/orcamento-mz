package mz.orcamento.backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mz.orcamento.backend.dto.despesa.DespesaRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "despesas")
@Getter
@Setter
public class Despesa {

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

    private LocalDateTime dataCriacao;

    public enum FaseExecucao {
        CABIMENTACAO, // Reserva do valor
        LIQUIDACAO,   // Verificação da fatura/serviço
        PAGAMENTO     // Saída do dinheiro
    }
    public Despesa(DespesaRequestDTO dto, Categoria categoria, User autorizador) {
        this.categoria = categoria;
        this.usuarioAutorizador = autorizador;
        this.descricaoFinalidade = dto.descricaoFinalidade();
        this.valorDespesa = dto.valorDespesa();
        this.nuitFornecedor = dto.nuitFornecedor();
        this.nomeFornecedor = dto.nomeFornecedor();
        this.coordenadasGeograficas = dto.coordenadasGeograficas();
        this.anexoFaturaUrl = dto.anexoFaturaUrl();
        this.anexoContratoUrl = dto.anexoContratoUrl();

        // Regra de Negócio: Toda despesa nova em Moçambique começa no Cabimento
        this.faseExecucao = FaseExecucao.CABIMENTACAO;

        // Data de registo no sistema
        this.dataCriacao = LocalDateTime.now();
    }
}