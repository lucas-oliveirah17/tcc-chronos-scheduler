package br.com.barberscheduler.backend.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "horarios_estabelecimento")
public class HorarioEstabelecimento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private DayOfWeek diaSemana;
    
    @Column(nullable = false)
    private LocalTime horaInicio;
    
    @Column(nullable = false)
    private LocalTime horaFim;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime atualizadoEm;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atualizado_por", nullable = false)
    private Usuario atualizadoPor;
    
    public HorarioEstabelecimento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DayOfWeek diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Usuario getAtualizadoPor() {
        return atualizadoPor;
    }

    public void setAtualizadoPor(Usuario atualizadoPor) {
        this.atualizadoPor = atualizadoPor;
    }
}
