package br.com.teaxis.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "evolucoes")
public class Evolucao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meta_id", nullable = false)
    private Meta meta;

    @Column(nullable = false)
    private LocalDate dataRegistro;

    private Double progresso;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    public Evolucao() {
    }

    public Evolucao(Meta meta, LocalDate dataRegistro, Double progresso, String comentario) {
        this.meta = meta;
        this.dataRegistro = dataRegistro;
        this.progresso = progresso;
        this.comentario = comentario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Double getProgresso() {
        return progresso;
    }

    public void setProgresso(Double progresso) {
        this.progresso = progresso;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}