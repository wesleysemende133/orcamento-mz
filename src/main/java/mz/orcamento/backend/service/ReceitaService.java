package mz.orcamento.backend.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mz.orcamento.backend.dto.receita.ReceitaRequestDTO;
import mz.orcamento.backend.dto.receita.ReceitaResponseDTO;
import mz.orcamento.backend.exepion.BusinessExeption;
import mz.orcamento.backend.model.Orcamento;
import mz.orcamento.backend.model.Receita;
import mz.orcamento.backend.repository.OrcamentoRepository;
import mz.orcamento.backend.repository.ReceitaRepository;

@Service
@RequiredArgsConstructor
public class ReceitaService {
    private final ReceitaRepository receitaRepository;
    private final OrcamentoRepository orcamentoRepository; // Supondo que você tenha este repository

    @Transactional
    public ReceitaResponseDTO salvarReceita(ReceitaRequestDTO dto) {
        // 1. Buscar o Orcamento usando o seu modelo
        Orcamento orcamento = orcamentoRepository.findById(dto.orcamentoId())
                .orElseThrow(() -> new BusinessExeption("Orçamento não encontrado."));

        // 2. Criar a Receita (usando o seu modelo original de Receita)
        Receita novaReceita = new Receita(dto, orcamento);

        // 3. ATUALIZAÇÃO: Usar o nome correto do campo do seu modelo
        BigDecimal novoTotalArrecadado = orcamento.getValorTotalArrecadado().add(dto.valor());
        orcamento.setValorTotalArrecadado(novoTotalArrecadado);

        // 4. Salvar e retornar
        return new ReceitaResponseDTO(receitaRepository.save(novaReceita));
    }
}
