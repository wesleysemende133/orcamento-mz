package mz.orcamento.backend.dto.user;

import jakarta.validation.constraints.*;
import mz.orcamento.backend.model.User;

import java.util.UUID;

/**
 * Representa o contrato de entrada para o registo de utilizadores.
 * As anotações garantem a integridade dos dados na camada de controller.
 */
public record UserRequestDTO(
        @NotBlank(message = "O nome de utilizador é obrigatório.")
        @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
        String nomeUsuario,

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O formato do email é inválido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres.")
        String senha,

        @NotBlank(message = "O BI é obrigatório.")
        @Pattern(regexp = "\\d{12}[A-Z]", message = "Formato de BI moçambicano inválido.")
        String numeroBI,

        @NotBlank(message = "O telefone é obrigatório.")
        String telefone,

        @NotNull(message = "O identificador do município é obrigatório.")
        UUID municipioId, // Enviamos apenas o ID, não o objeto completo

        @NotNull(message = "O perfil de acesso deve ser definido.")
        User.PerfilAcesso perfilAcesso
) {}