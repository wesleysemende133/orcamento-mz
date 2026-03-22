package mz.orcamento.backend.dto.municipio;

import mz.orcamento.backend.model.Municipio;

import java.util.UUID;

public record MunicipioResponseDTO(
        UUID id,
        String nomeAutarquia,
        String provincia,
        String nuitInstitucional,
        Municipio.ClassificacaoAutarquia classificacaoAutarquia
        ) {
}