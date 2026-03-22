package mz.orcamento.backend.dto.user;
import java.util.UUID;

/**
 * Projeção de saída para dados do utilizador.
 * Nota técnica: Ocultamos a senha para conformidade com normas de segurança.
 */
public record UserResponseDTO(
        UUID id,             // O erro acontecia porque o ID não estava aqui no início
        String nomeUsuario,   // Corrigido o erro de digitação "nomeUuario"
        String email,
        String numeroBI,
        String telefone,
        String nomeMunicipio, // Mais útil para o Frontend que apenas o UUID
        String perfilAcesso   // Alterado de boolean para String
) {}