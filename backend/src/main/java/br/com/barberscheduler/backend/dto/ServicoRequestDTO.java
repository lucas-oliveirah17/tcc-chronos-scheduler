package br.com.barberscheduler.backend.dto;

import java.math.BigDecimal;

/**
 * Record para RECEBER dados ao criar ou atualizar um Servico.
 */
public record ServicoRequestDTO(
    String nome,
    String descricao,
    Integer duracaoMinutos,
    BigDecimal preco
) {
}