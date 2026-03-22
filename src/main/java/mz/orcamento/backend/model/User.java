package mz.orcamento.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id", nullable = false)
    private Municipio municipio;

    @Column(unique = true, nullable = false)
    private String nomeUsuario;

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

    @Enumerated(EnumType.STRING) // Salva o nome (ex: 'AUDITOR_EXTERNO') no BD
    @Column(nullable = false)
    private PerfilAcesso perfilAcesso;

    public enum PerfilAcesso{
        ADMINISTRADOR_SISTEMA,
        GESTOR_FINANCEIRO,
        AUDITOR_EXTERNO,
        MUNICIPE_CONSULTA
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte o seu Enum PerfilAcesso em uma autoridade do Spring
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.perfilAcesso.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email; // O email será o nosso "username" de login
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return this.statusConta; }
}