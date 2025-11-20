package br.com.barberscheduler.backend.dto;

import br.com.barberscheduler.backend.model.Usuario;

public class AutenticacaoRequestDTO {
    private String email;
    private String senha;
    
    public AutenticacaoRequestDTO() {
    }
    
    public AutenticacaoRequestDTO(Usuario usuario) {
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
