package br.com.barberscheduler.backend.dto;

public class AutenticacaoResponseDTO {
    private String token;
    private UsuarioDTO usuario;

    public AutenticacaoResponseDTO(
            String token, 
            UsuarioDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }
    
    public String getToken() {
        return token;
    }
    
    public UsuarioDTO getUsuario() {
        return usuario;
    }
}
