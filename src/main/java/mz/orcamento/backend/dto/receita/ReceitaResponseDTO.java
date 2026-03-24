package mz.orcamento.backend.dto.receita;

import mz.orcamento.backend.model.Receita;
import mz.orcamento.backend.model.Receita.TipoReceita;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReceitaResponseDTO(
        UUID idReceita,
        BigDecimal valorArrecado,
        LocalDate dataArrecadado,
        TipoReceita tipoReceita,
        String comprovativoDepositoUrl,
        UUID orcamentoId
) {
    // ESTE É O CONSTRUTOR QUE O SERVICE CHAMA: new ReceitaResponseDTO(novaReceita)
    public ReceitaResponseDTO(Receita receita) {
        this(
                receita.getIdReceita(),
                receita.getValorArrecado(),
                receita.getDataArrecadado(),
                receita.getTipoReceita(),
                receita.getComprovativoDepositoUrl(),
                receita.getOrcamento().getIdOrcamento()
        );
    }
}
