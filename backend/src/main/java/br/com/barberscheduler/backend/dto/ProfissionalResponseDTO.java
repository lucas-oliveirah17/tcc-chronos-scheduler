package br.com.barberscheduler.backend.dto;

import br.com.barberscheduler.backend.model.Profissional;

public record ProfissionalResponseDTO(
    Long id,
    String especialidades,
    UsuarioResponseDTO usuario
) {
    public ProfissionalResponseDTO(Profissional profissional) {
        this(
            profissional.getId(),
            profissional.getEspecialidades(),
            new UsuarioResponseDTO(profissional.getUsuario())
        );
    }
}