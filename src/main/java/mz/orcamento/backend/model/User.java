package mz.orcamento.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mz.orcamento.backend.dto.UserDTO;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String numeroBI;
    private String residencia;
    private String email;
    private String telefone;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date dataCadastro;

    public UserDTO convert(){
        UserDTO dto =new UserDTO();
        dto.setNome(this.nome);
        dto.setNumeroBI(this.numeroBI);
        dto.setResidencia(this.residencia);
        dto.setEmail(this.email);
        dto.setTelefone(this.telefone);
        dto.setDataCadastro(this.dataCadastro);
        return dto;
    }
}