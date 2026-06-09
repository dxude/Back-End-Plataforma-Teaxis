package br.com.teaxis.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "matchings")
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profesional;

    private String status; 
    private LocalDate dataSugestao;

    // --- MÉTODOS GETTERS E SETTERS MANUAIS PARA FORÇAR A COMPILAÇÃO ---
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Profissional getProfissional() {
        return profesional;
    }

    public void setProfissional(Profissional profissional) {
        this.profesional = profissional;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataSugestao() {
        return dataSugestao;
    }

    public void setDataSugestao(LocalDate dataSugestao) {
        this.dataSugestao = dataSugestao;
    }
}