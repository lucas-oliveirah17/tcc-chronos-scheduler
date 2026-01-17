package br.com.chronos.agendamentos.backend.dto;

import br.com.chronos.agendamentos.backend.model.Usuario;

public record AutenticacaoRequestDTO(
    String email,
    String senha
) {
    public AutenticacaoRequestDTO(Usuario usuario) {
        this(
    		usuario.getEmail(), 
			usuario.getSenha()
		);
    }
}