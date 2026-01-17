package br.com.chronos.agendamentos.backend.dto;

public record AutenticacaoResponseDTO(
    String token,
    UsuarioResponseDTO usuario
) {
}