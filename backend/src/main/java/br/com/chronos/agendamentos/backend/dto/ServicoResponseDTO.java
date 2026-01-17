package br.com.chronos.agendamentos.backend.dto;

import java.math.BigDecimal;

import br.com.chronos.agendamentos.backend.model.Servico;

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