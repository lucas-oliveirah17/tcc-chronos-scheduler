package br.com.chronos.agendamentos.backend.dto;

import br.com.chronos.agendamentos.backend.model.Profissional;

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