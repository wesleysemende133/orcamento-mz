package mz.orcamento.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mz.orcamento.backend.dto.despesa.DespesaRequestDTO;
import mz.orcamento.backend.dto.despesa.DespesaResponseDTO;
import mz.orcamento.backend.service.DespesaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/despesas")
@RequiredArgsConstructor
public class DespesaController {

    private final DespesaService despesaService;

    @PostMapping
    public ResponseEntity<DespesaResponseDTO> registrarDespesa(@RequestBody @Valid DespesaRequestDTO dto) {
        DespesaResponseDTO response = despesaService.salvarDespesa(dto);

        // Retorna o status 201 (Created) que é a norma para novos registos numa API REST
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}