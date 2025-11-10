package br.com.barberscheduler.backend.dto;

// DTO para RECEBER dados ao criar ou atualizar um Profissional
public class ProfissionalRequestDTO {
    
    private String especialidades;
    private Long usuarioId;
    
    public ProfissionalRequestDTO() {
    }

    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
        this.especialidades = especialidades;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
