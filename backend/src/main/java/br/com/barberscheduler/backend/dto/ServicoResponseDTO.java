package br.com.barberscheduler.backend.dto;

import java.math.BigDecimal;
import br.com.barberscheduler.backend.model.Servico;

/**
 * Record para RETORNAR dados de Servico ao frontend.
 * Records são imutáveis e ideais para transferência de dados.
 */
public record ServicoResponseDTO(
    Long id,
    String nome,
    String descricao,
    Integer duracaoMinutos,
    BigDecimal preco
) {
    public ServicoResponseDTO(Servico servico) {
        this(
            servico.getId(),
            servico.getNome(),
            servico.getDescricao(),
            servico.getDuracaoMinutos(),
            servico.getPreco()
        );
    }
}