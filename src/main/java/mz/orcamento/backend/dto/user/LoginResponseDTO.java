package mz.orcamento.backend.dto.user;


public record LoginResponseDTO(
        String token,           // O JWT gerado pelo TokenService
        String tipo,            // Geralmente "Bearer"
        UserResponseDTO usuario // Dados do perfil para exibição no Dashboard (Nome, Cargo, etc.)
) {}