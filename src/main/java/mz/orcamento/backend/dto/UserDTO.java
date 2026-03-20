package mz.orcamento.backend.dto;

import lombok.Getter;
import lombok.Setter;
import mz.orcamento.backend.model.User;

import java.util.Date;
@Getter
@Setter
public class UserDTO {
    private String nome;
    private String numeroBI;
    private String residencia;
    private String email;
    private String telefone;
    private Date dataCadastro;

}
