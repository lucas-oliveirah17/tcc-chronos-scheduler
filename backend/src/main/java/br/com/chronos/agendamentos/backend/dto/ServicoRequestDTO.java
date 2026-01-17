package br.com.chronos.agendamentos.backend.dto;

import java.math.BigDecimal;

public record ServicoRequestDTO(
    String nome,
    String descricao,
    Integer duracaoMinutos,
    BigDecimal preco
) {
}