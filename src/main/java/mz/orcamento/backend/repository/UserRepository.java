package mz.orcamento.backend.repository;

import mz.orcamento.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {

    User findbyBI(String bi);
    List <User> queryByNomeLike(String nome);
}
