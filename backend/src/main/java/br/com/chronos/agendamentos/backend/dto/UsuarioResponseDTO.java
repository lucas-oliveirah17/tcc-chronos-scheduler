package br.com.chronos.agendamentos.backend.dto;

import java.time.LocalDateTime;

import br.com.chronos.agendamentos.backend.model.Usuario;
import br.com.chronos.agendamentos.backend.model.enums.PerfilUsuario;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String email,
    String telefone,
    PerfilUsuario perfil,
    LocalDateTime criadoEm
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTelefone(),
            usuario.getPerfil(),
            usuario.getCriadoEm()
        );
    }
}