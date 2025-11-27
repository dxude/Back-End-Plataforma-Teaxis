package br.com.teaxis.api.model;

import jakarta.persistence.*;
import lombok.Data; 
import java.time.LocalDate;

@Data 
@Entity
public class Evolucao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataRegistro;
    

    private String progresso; 
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "meta_id")
    private Meta meta;
}