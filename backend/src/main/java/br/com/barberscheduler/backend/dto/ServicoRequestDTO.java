package br.com.barberscheduler.backend.dto;

import java.math.BigDecimal;

public record ServicoRequestDTO(
    String nome,
    String descricao,
    Integer duracaoMinutos,
    BigDecimal preco
) {
}