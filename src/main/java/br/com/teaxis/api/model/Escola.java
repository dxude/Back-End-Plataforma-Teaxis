package br.com.teaxis.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data 
@Entity
@Table(name = "escolas")
public class Escola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false, length = 2) 
    private String estado;

    @Column(nullable = false)
    private String contato;
}