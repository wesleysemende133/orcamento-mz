package mz.orcamento.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mz.orcamento.backend.model.User;
import mz.orcamento.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

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
    @PostMapping
    public ResponseEntity<User> criar(@RequestBody @Valid User user) {
        // Nota: Numa aplicação real, aqui faríamos o hash da password
        User novoUsuario = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }
}
