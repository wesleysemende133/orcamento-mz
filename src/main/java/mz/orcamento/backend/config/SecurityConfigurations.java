package mz.orcamento.backend.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {

                    req.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

                    req.requestMatchers(HttpMethod.POST, "/api/usuarios/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/api/usuarios/registrar").permitAll();

                    req.requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMINISTRADOR_SISTEMA");
                    req.requestMatchers(HttpMethod.POST, "/api/municipios").hasRole("ADMINISTRADOR_SISTEMA");
                    req.requestMatchers(HttpMethod.POST, "/api/orcamentos/**").hasAnyRole("ADMINISTRADOR_SISTEMA", "GESTOR_FINANCEIRO");

                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 1. Aceita qualquer origem (IP, localhost, domínio real)
        // O use de setAllowedOriginPatterns com "*" é mais seguro que setAllowedOrigins("*")
        // quando setAllowCredentials é true.
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));

        // 2. Aceita todos os métodos HTTP (GET, POST, PUT, DELETE, PATCH, OPTIONS, etc.)
        configuration.setAllowedMethods(Arrays.asList("*"));

        // 3. Aceita todos os cabeçalhos (Headers) enviados pelo Axios/Browser
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // 4. Permite envio de Cookies/Autenticação
        configuration.setAllowCredentials(true);

        // 5. Expõe headers específicos se o frontend precisar ler algo do cabeçalho de resposta
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
