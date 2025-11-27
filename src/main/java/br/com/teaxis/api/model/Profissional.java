package br.com.teaxis.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "profissionais")
@EqualsAndHashCode(of = "id")
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Column(columnDefinition = "TEXT")
    private String disponibilidade;

    private Double avaliacaoMedia = 0.0;

    
    @Column(columnDefinition = "TEXT")
    private String certificacoes; 

    @Column(columnDefinition = "TEXT")
    private String especializacoes; 

    @Column(columnDefinition = "TEXT")
    private String metodosUtilizados; 

    
    @Column(columnDefinition = "TEXT")
    private String hobbies; 
}