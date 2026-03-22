package mz.orcamento.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import mz.orcamento.backend.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret; // Chave mestra definida no application.properties

    /**
     * Gera um token assinado para o utilizador autenticado.
     */
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("orcamento-mz-api")
                    .withSubject(user.getEmail()) // Identificador principal
                    .withExpiresAt(genExpirationDate()) // Tempo de vida do token
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token de acesso", exception);
        }
    }

    /**
     * Valida o token e extrai o e-mail do utilizador (Subject).
     * Este é o método que estava a faltar no seu código.
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("orcamento-mz-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return ""; // Retorna vazio se o token for inválido ou expirado
        }
    }

    private Instant genExpirationDate() {
        // Define expiração para 2 horas (fuso horário de Maputo)
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("+02:00"));
    }
}
