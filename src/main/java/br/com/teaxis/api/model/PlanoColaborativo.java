package br.com.teaxis.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "planos_colaborativos")
public class PlanoColaborativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    
    @ManyToOne
    @JoinColumn(name = "escola_id") 
    private Escola escola;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String objetivoGeral;

    @Column(columnDefinition = "TEXT")
    private String observacoesCompartilhadas;
}