package br.com.barberscheduler.backend.dto;

import br.com.barberscheduler.backend.model.enums.PerfilUsuario;

public record UsuarioRequestDTO(
    String nome,
    String email,
    String senha,
    String telefone,
    PerfilUsuario perfil
) {
}