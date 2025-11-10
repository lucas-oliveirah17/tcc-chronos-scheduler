package br.com.barberscheduler.backend.dto;

//DTO para RETORNAR dados de Profissional ao frontend
public class ProfissionalDTO {
    
    private Long id;
    private String especialidades;
    private UsuarioDTO usuario;
    
    public ProfissionalDTO() {        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
        this.especialidades = especialidades;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
}
