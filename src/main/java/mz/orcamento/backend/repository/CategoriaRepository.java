package mz.orcamento.backend.repository;

import mz.orcamento.backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

    /**
     * MUDANÇA AQUI: O método agora referencia 'ClassificadorFuncional'
     * para bater com o atributo da sua Entity.
     */
    boolean existsByClassificadorFuncionalIgnoreCase(String classificadorFuncional);

    /**
     * Retorna as categorias ativas.
     * Verifique se o atributo na sua Entity Categoria se chama 'status'.
     */
    List<Categoria> findAllByStatusTrue();

    /**
     * Se você adicionou o campo 'codigoRubrica' na Entity,
     * este método permite buscar uma rubrica específica (ex: 311.01).
     */
    List<Categoria> findByCodigoRubrica(String codigoRubrica);
}