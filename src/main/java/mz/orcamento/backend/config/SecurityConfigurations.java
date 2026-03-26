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
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // 1. O Login e o Registro de novos usuários (públicos por enquanto)
                    req.requestMatchers(HttpMethod.POST, "/api/usuarios/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/api/usuarios/registrar").permitAll();
                    req.requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMINISTRADOR_SISTEMA");

                    // 2. Criação de Municípios: Só quem é ADMIN pode fazer
                    req.requestMatchers(HttpMethod.POST, "/api/municipios").permitAll();///depois atualizar para que apenas o ADM do sistema crie

                    // 3. Orçamentos: Só ADMIN ou GESTOR_FINANCEIRO podem criar/editar
                    req.requestMatchers(HttpMethod.POST, "/api/orcamentos/**").hasAnyRole("ADMINISTRADOR_SISTEMA", "GESTOR_FINANCEIRO");

                    // 4. Todo o resto exige estar logado (Token válido)
                    req.anyRequest().authenticated();
                })
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
