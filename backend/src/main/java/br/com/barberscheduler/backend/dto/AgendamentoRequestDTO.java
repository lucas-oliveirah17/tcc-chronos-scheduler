package br.com.barberscheduler.backend.dto;

import java.time.LocalDateTime;

public record AgendamentoRequestDTO(
    Long clienteId,
    Long profissionalId,
    Long servicoId,
    LocalDateTime dataHoraInicio
) {
}