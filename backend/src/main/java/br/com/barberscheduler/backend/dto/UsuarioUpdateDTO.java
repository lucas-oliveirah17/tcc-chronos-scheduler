package br.com.barberscheduler.backend.dto;

// DTO para RECEBER dados ao atualizar um Usuario
public class UsuarioUpdateDTO {
    
    private String nome;
    private String email;
    private String telefone;
    
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
}
