package br.com.barberscheduler.backend.dto;

import java.math.BigDecimal;

import br.com.barberscheduler.backend.model.Servico;

// DTO para RETORNAR dados de Servico ao frontend
public class ServicoDTO {
    
    private Long id;
    private String nome;
    private String descricao;
    private Integer duracaoMinutos;
    private BigDecimal preco;
    
    public ServicoDTO() {
    }
    
    public ServicoDTO(Servico servico) {
        this.id = servico.getId();
        this.nome = servico.getNome();
        this.descricao = servico.getDescricao();
        this.duracaoMinutos = servico.getDuracaoMinutos();
        this.preco = servico.getPreco();
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
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }
    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }
    public BigDecimal getPreco() {
        return preco;
    }
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    } 
}
