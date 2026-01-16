package br.com.barberscheduler.backend.dto;

public class AutenticacaoResponseDTO {
    private String token;
    private UsuarioResponseDTO usuario;

    public AutenticacaoResponseDTO(
            String token, 
            UsuarioResponseDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }
    
    public String getToken() {
        return token;
    }
    
    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }
}
