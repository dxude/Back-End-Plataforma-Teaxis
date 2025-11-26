package br.com.teaxis.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
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
}