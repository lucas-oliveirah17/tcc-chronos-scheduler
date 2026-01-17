package br.com.chronos.agendamentos.backend.dto;

import br.com.chronos.agendamentos.backend.model.enums.PerfilUsuario;

public record UsuarioRequestDTO(
    String nome,
    String email,
    String senha,
    String telefone,
    PerfilUsuario perfil
) {
}