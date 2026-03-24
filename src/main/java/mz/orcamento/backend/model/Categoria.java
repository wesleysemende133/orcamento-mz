package mz.orcamento.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mz.orcamento.backend.dto.categoria.CategoriaRequestDTO;


import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCategoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orcamento_id", nullable = false)
    private Orcamento orcamento;

    @Column(nullable = false)
    private String classificadorFuncional;

    @Column(nullable = false)
    private String codigoRubrica;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal dotacaoInicial;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal saldoDisponivel;

    @Column(columnDefinition = "TEXT")
    private String justificativaImpacto;

    private boolean status;

    public Categoria(CategoriaRequestDTO dto) {
        this.classificadorFuncional = dto.classificadorFuncional();
        this.codigoRubrica = dto.codigoRubrica();
        this.dotacaoInicial = dto.dotacaoInicial();
        this.justificativaImpacto = dto.justificativaImpacto();
        this.status = true; // Toda nova categoria nasce "Ativa"
    }
}
