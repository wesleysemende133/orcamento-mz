package mz.orcamento.backend.service;

import lombok.RequiredArgsConstructor;
import mz.orcamento.backend.dto.categoria.CategoriaRequestDTO;
import mz.orcamento.backend.dto.categoria.CategoriaResponseDTO;
import mz.orcamento.backend.exepion.BusinessExepion; // Verifique se o nome da pasta é 'exepion' ou 'exception'
import mz.orcamento.backend.model.Categoria;
import mz.orcamento.backend.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaSevice {
    private final CategoriaRepository categoriaRepository;

    @Transactional
    public CategoriaResponseDTO criar(CategoriaRequestDTO dto) {
        if (categoriaRepository.existsByClassificadorFuncionalIgnoreCase(dto.classificadorFuncional())) {
            throw new BusinessExepion("Este classificador funcional ja se encontra registado");
        }

        Categoria novaCategoria = new Categoria(dto);
        Categoria categoriaSalva = categoriaRepository.save(novaCategoria);

        return new CategoriaResponseDTO(categoriaSalva); // Agora o tipo coincide com o retorno do método
    }

    public List<CategoriaResponseDTO> listarAtivas() {
        return categoriaRepository.findAllByStatusTrue()
                .stream()
                .map(CategoriaResponseDTO::new)
                .collect(Collectors.toList());
    }
}
