package br.com.barberscheduler.backend.dto;

import br.com.barberscheduler.backend.model.Usuario;

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