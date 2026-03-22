package mz.orcamento.backend.dto.municipio;

import mz.orcamento.backend.model.Municipio;

public record MunicipioRequestDTO(
    String nomeAutarquia,
    String provincia,
    String nuitInstitucional,
    Integer populacaoEstimada,
    Municipio.ClassificacaoAutarquia classificacaoAutarquia
){}
