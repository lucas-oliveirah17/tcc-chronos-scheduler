package br.com.barberscheduler.backend.dto;

import br.com.barberscheduler.backend.model.Usuario;

public class AutenticacaoDTO {
    private String email;
    private String senha;
    
    public AutenticacaoDTO() {
    }
    
    public AutenticacaoDTO(Usuario usuario) {
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
