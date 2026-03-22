package mz.orcamento.backend.repository;

import mz.orcamento.backend.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface de persistência para a entidade Município.
 * Estende JpaRepository para herdar operações padrão de CRUD e paginação.
 */
@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, UUID> {

    /**
     * Procura um município pelo seu NUIT institucional.
     * Utilizado para garantir a unicidade da autarquia no sistema.
     */
    Optional<Municipio> findByNuitInstitucional(String nuitInstitucional);

    /**
     * Verifica se já existe um município registado com um determinado nome.
     */
    boolean existsByNomeAutarquia(String nomeAutarquia);
}
