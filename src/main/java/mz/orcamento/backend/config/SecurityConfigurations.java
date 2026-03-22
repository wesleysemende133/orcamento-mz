package mz.orcamento.backend.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter; // Filtro personalizado que verifica o Token JWT

    /**
     * Define a corrente de filtros de segurança (Security Filter Chain).
     * Configura a aplicação como Stateless e define as permissões de acesso.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desativado pois o JWT protege contra CSRF
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // Endpoints Públicos
                    req.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/auth/registrar").permitAll();

                    // Endpoints Restritos por Perfil (RBAC)
                    req.requestMatchers("/api/orcamentos/**").hasAnyRole("ADMINISTRADOR_SISTEMA", "GESTOR_FINANCEIRO");
                    req.requestMatchers("/api/auditoria/**").hasRole("AUDITOR_EXTERNO");

                    // Qualquer outra requisição deve ser autenticada
                    req.anyRequest().authenticated();
                })
                // Adiciona o nosso filtro de Token antes do filtro de autenticação padrão do Spring
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Expõe o AuthenticationManager para o UserService.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Define o algoritmo de hashing para as senhas.
     * O BCrypt é o padrão ouro para armazenamento seguro de credenciais.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
