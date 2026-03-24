package mz.orcamento.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mz.orcamento.backend.dto.receita.ReceitaRequestDTO;
import mz.orcamento.backend.dto.receita.ReceitaResponseDTO;
import mz.orcamento.backend.service.ReceitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receitas")
@RequiredArgsConstructor
public class ReceitaController {

    private final ReceitaService receitaService;

    @PostMapping
    public ResponseEntity<ReceitaResponseDTO> registrarReceita(@RequestBody @Valid ReceitaRequestDTO dto) {
        ReceitaResponseDTO response = receitaService.salvarReceita(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}