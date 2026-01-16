package br.com.barberscheduler.backend.dto;

import br.com.barberscheduler.backend.model.enums.PerfilUsuario;

public record UsuarioUpdateRequestDTO(
    String nome,
    String email,
    String telefone,
    PerfilUsuario perfil
) {
}