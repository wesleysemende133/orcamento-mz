package mz.orcamento.backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Contrato de entrada para o processo de autenticação (Sign-in).
 * Este DTO transporta as credenciais necessárias para a geração do token JWT.
 */
public record LoginRequestDTO(
        @NotBlank(message = "O email é obrigatório para o acesso.")
        @Email(message = "Formato de email inválido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
        String senha
) {}
