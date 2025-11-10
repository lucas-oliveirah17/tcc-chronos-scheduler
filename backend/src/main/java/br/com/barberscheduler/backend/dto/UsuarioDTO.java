package br.com.barberscheduler.backend.dto;

import br.com.barberscheduler.backend.model.Usuario;
import br.com.barberscheduler.backend.model.enums.PerfilUsuario;

import java.time.LocalDateTime;

// DTO para RETORNAR dados de Usuario ao frontend
public class UsuarioDTO {
    
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private PerfilUsuario perfil;
    private LocalDateTime criadoEm;
    
    public UsuarioDTO() {
    }
    
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.telefone = usuario.getTelefone();
        this.perfil = usuario.getPerfil();
        this.criadoEm = usuario.getCriadoEm();
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
