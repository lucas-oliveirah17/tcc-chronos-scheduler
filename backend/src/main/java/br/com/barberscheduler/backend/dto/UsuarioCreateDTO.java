package br.com.barberscheduler.backend.dto;

import br.com.barberscheduler.backend.model.enums.PerfilUsuario;

//DTO para RECEBER dados ao criar um novo Usuario
public class UsuarioCreateDTO {
    
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private PerfilUsuario perfil;
    
    public UsuarioCreateDTO() {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
