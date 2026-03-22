package mz.orcamento.backend.repository;


import mz.orcamento.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 1. O Spring Security precisa que o retorno seja UserDetails ou Optional
    // Note a correção: 'findByEmail' (o 'b' deve ser maiúsculo)
    Optional<User> findByEmail(String email);

    // 2. Para a autenticação interna do Spring Security
    UserDetails findUserDetailsByEmail(String email);

    // 3. Busca por nome (está correto, mas 'Containing' costuma ser mais flexível que 'Like')
    List<User> findByNomeContainingIgnoreCase(String nome);
}
