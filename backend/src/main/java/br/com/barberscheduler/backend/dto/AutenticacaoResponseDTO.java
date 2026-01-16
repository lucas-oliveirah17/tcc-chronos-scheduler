package br.com.barberscheduler.backend.dto;

public record AutenticacaoResponseDTO(
    String token,
    UsuarioResponseDTO usuario
) {
}