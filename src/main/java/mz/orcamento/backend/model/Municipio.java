package mz.orcamento.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idMunicipio;

    @Column(unique = true, nullable = false)
    private String nomeAutarquia;

    @Column(nullable = false)
    private String provincia;

    @Column(unique = true,nullable = false, length = 9)
    private String nuitIntitucional;

    private Integer populacaoEstimada;

    public enum ClassificacaoAutarquia{
        CIDADE_NIVEL_A,
        CIDADE_NIVEL_B,
        CIDADE_NIVEL_C,
        VILA
    }
}
