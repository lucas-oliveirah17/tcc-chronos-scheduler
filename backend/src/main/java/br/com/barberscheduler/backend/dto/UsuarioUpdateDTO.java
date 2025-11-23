package br.com.barberscheduler.backend.dto;

import br.com.barberscheduler.backend.model.enums.PerfilUsuario;

// DTO para RECEBER dados ao atualizar um Usuario
public class UsuarioUpdateDTO {
    
    private String nome;
    private String email;
    private String telefone;
    private PerfilUsuario perfil;
    
    public UsuarioUpdateDTO() {        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }  
}
