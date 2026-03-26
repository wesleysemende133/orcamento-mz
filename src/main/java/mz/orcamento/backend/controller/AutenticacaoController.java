package mz.orcamento.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mz.orcamento.backend.config.TokenService;
import mz.orcamento.backend.dto.user.LoginRequestDTO;
import mz.orcamento.backend.dto.user.LoginResponseDTO;
import mz.orcamento.backend.dto.user.UserResponseDTO;
import mz.orcamento.backend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDTO dados) {
        // 1. Autenticação inicial
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        // 2. Recuperar o usuário do contexto de autenticação
        var user = (User) authentication.getPrincipal();

        // 3. Gerar o Token usando o seu método 'generateToken'
        var tokenJWT = tokenService.generateToken(user);

        // 4. Montar o UserResponseDTO com os 7 campos exatos
        // Note o uso de .name() no Enum para passar como String para o DTO
        var userDTO = new UserResponseDTO(
                user.getId(),
                user.getNomeUsuario(),
                user.getEmail(),
                user.getNumeroBI(),
                user.getTelefone(),
                user.getMunicipio() != null ? user.getMunicipio().getNomeAutarquia() : "Não vinculado",
                user.getPerfilAcesso().name()
        );

        // 5. Retornar a resposta completa
        return ResponseEntity.ok(new LoginResponseDTO(tokenJWT, "Bearer", userDTO));
    }
}
