package br.com.barberscheduler.backend.dto;

public class TokenDTO {
    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return token;
    }
}
