package mz.orcamento.backend.controller;

import lombok.RequiredArgsConstructor;
import mz.orcamento.backend.model.Municipio;
import mz.orcamento.backend.repository.MunicipioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/municipios")
@RequiredArgsConstructor
public class MunicipioController {

    private final MunicipioRepository municipioRepository;

    @PostMapping
    public ResponseEntity<Municipio> criar(@RequestBody Municipio municipio) {
        Municipio novo = municipioRepository.save(municipio);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @GetMapping
    public ResponseEntity<List<Municipio>> listar() {
        return ResponseEntity.ok(municipioRepository.findAll());
    }
}