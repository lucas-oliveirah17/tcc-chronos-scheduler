package br.com.chronos.agendamentos.backend.dto;

import java.time.LocalDateTime;

public record AgendamentoRequestDTO(
    Long clienteId,
    Long profissionalId,
    Long servicoId,
    LocalDateTime dataHoraInicio
) {
}