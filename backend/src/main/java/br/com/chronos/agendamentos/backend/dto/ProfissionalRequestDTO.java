package br.com.chronos.agendamentos.backend.dto;

public record ProfissionalRequestDTO(
    String especialidades,
    Long usuarioId
) {
}