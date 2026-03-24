package mz.orcamento.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mz.orcamento.backend.dto.categoria.CategoriaRequestDTO;
import mz.orcamento.backend.model.Categoria;
import mz.orcamento.backend.repository.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodas() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@RequestBody @Valid CategoriaRequestDTO dto) {
        // Usando o construtor que você criou na Entity
        Categoria novaCategoria = new Categoria(dto);
        // Nota: Você precisará setar o Orcamento aqui ou no Service antes de salvar
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(novaCategoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable UUID id, @RequestBody @Valid CategoriaRequestDTO dto) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    // Atualizando com os seus nomes de atributos reais
                    categoria.setClassificadorFuncional(dto.classificadorFuncional());
                    categoria.setCodigoRubrica(dto.codigoRubrica());
                    categoria.setJustificativaImpacto(dto.justificativaImpacto());
                    return ResponseEntity.ok(categoriaRepository.save(categoria));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}