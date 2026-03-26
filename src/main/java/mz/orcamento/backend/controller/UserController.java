package mz.orcamento.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mz.orcamento.backend.model.User;
import mz.orcamento.backend.repository.MunicipioRepository;
import mz.orcamento.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MunicipioRepository municipioRepository;

    // Listar todos os utilizadores (Útil para selecionar o 'Autorizador' no Frontend)
    @GetMapping
    public ResponseEntity<List<User>> listarTodos() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // Buscar um utilizador específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> buscarPorId(@PathVariable UUID id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo utilizador (Ex: Novo funcionário das Finanças)
    @PostMapping("/registrar")
    @Transactional // Importante para garantir que a transação seja finalizada corretamente
    public ResponseEntity registrar(@RequestBody User user) {
        try {
            // 1. O Hibernate precisa de um objeto Municipio que venha do Banco (Managed)
            // Se o JSON enviou apenas o ID, precisamos buscá-lo.
            if (user.getMunicipio() == null || user.getMunicipio().getIdMunicipio() == null) {
                return ResponseEntity.badRequest().body("O ID do município é obrigatório.");
            }

            var municipioDoBanco = municipioRepository.findById(user.getMunicipio().getIdMunicipio())
                    .orElseThrow(() -> new RuntimeException("Município não encontrado no banco de dados!"));

            // 2. Substituímos o município "transiente" pelo "managed" (do banco)
            user.setMunicipio(municipioDoBanco);

            // 3. Criptografamos a senha (Padrão de Segurança)
            user.setSenha(passwordEncoder.encode(user.getSenha()));

            // 4. Garantimos que a conta comece ativa
            user.setStatusConta(true);

            // 5. Salvamos o usuário
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar: " + e.getMessage());
        }
    }
    // Deletar um utilizador por ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR_SISTEMA')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content (sucesso sem corpo)
    }
}
