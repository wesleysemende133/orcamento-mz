package mz.orcamento.backend.repository;

import mz.orcamento.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID; // IMPORTANTE: Importar o UUID

@Repository
public interface UserRepository extends JpaRepository<User, UUID> { // CORREÇÃO: Long -> UUID

    // O Spring Security utiliza este método para carregar o utilizador durante o login
    Optional<User> findByEmail(String email);

    // Método específico para retornar o contrato UserDetails do Spring Security
    UserDetails findUserDetailsByEmail(String email);

    // Busca flexível: encontra "João" mesmo se pesquisar "joao"
    List<User> findByNomeUsuarioContainingIgnoreCase(String nomeUsuario);
}
