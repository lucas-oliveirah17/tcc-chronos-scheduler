package br.com.chronos.agendamentos.backend.dto;

import br.com.chronos.agendamentos.backend.model.enums.PerfilUsuario;

public record UsuarioUpdateRequestDTO(
    String nome,
    String email,
    String telefone,
    PerfilUsuario perfil
) {
}