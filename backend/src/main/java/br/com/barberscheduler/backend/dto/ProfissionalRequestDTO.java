package br.com.barberscheduler.backend.dto;

public record ProfissionalRequestDTO(
    String especialidades,
    Long usuarioId
) {
}