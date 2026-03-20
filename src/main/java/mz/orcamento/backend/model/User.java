package mz.orcamento.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mz.orcamento.backend.dto.UserDTO;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id", nullable = false)
    private Municipio municipio;

    @Column(unique = true, nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String senha;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String numeroBI;
    private String telefone;

    @Column(nullable = false)
    private boolean statusConta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date dataCadastro;

    public enum PerfilAcesso{
        ADMINISTRADOR_SISTEMA,
        GESTOR_FINANCEIRO,
        AUDITOR_EXTERNO,
        MUNICIPE_CONSULTA
    }
}