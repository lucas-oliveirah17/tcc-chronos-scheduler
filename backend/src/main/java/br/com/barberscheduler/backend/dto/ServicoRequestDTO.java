package br.com.barberscheduler.backend.dto;

import java.math.BigDecimal;

// DTO para RECEBER dados ao criar ou atualizar um Servico
public class ServicoRequestDTO {
    
    private String nome;
    private String descricao;
    private Integer duracaoMinutos;
    private BigDecimal preco;
    
    public ServicoRequestDTO() {
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
