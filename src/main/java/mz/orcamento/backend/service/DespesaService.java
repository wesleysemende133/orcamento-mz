package mz.orcamento.backend.service;

import lombok.RequiredArgsConstructor;
import mz.orcamento.backend.dto.despesa.DespesaRequestDTO;
import mz.orcamento.backend.dto.despesa.DespesaResponseDTO;
import mz.orcamento.backend.exepion.BusinessExeption;
import mz.orcamento.backend.model.Categoria;
import mz.orcamento.backend.model.Despesa;
import mz.orcamento.backend.model.User;
import mz.orcamento.backend.repository.CategoriaRepository;
import mz.orcamento.backend.repository.DespesaRepository;
import mz.orcamento.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DespesaService {
    private final DespesaRepository despesaRepository;
    private final CategoriaRepository categoriaRepository;
    private final UserRepository userRepository;

    @Transactional
    public DespesaResponseDTO salvarDespesa(DespesaRequestDTO dto){
        if (dto.valorDespesa().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessExeption("O valor da despesa deve ser superior a zero.");
        }
        //2.Buscar e Validar Categoria/Saldo
        // CORRETO: Usando a variável injetada pelo @RequiredArgsConstructor
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new BusinessExeption("Categoria não encontrada."));

        if (categoria.getSaldoDisponivel().compareTo(dto.valorDespesa()) < 0) {
            throw new BusinessExeption("Saldo insuficiente para esta rubrica.");
        }

        // 3. Buscar Autorizador
        User autorizador = userRepository.findById(dto.usuarioAutorizadorId())
                .orElseThrow(() -> new BusinessExeption("Usuário autorizador inválido."));

        // 4. Persistir e atualizar saldo da categoria
        Despesa novaDespesa = new Despesa(dto, categoria, autorizador);
        categoria.setSaldoDisponivel(categoria.getSaldoDisponivel().subtract(dto.valorDespesa()));

        return new DespesaResponseDTO(despesaRepository.save(novaDespesa));
    }
}
