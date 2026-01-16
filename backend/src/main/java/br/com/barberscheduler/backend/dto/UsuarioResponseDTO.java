package br.com.barberscheduler.backend.dto;

import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.model.enums.PerfilUsuario;
import java.time.LocalDateTime;

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