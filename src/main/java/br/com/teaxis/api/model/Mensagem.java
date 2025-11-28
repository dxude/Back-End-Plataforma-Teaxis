package br.com.teaxis.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entidade JPA que representa uma mensagem enviada entre usuários.
 */
@Entity
@Table(name = "mensagens")
@Data 
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long remetenteId;      
    private Long destinatarioId;  

    @Column(nullable = false)
    private String conteudo;       

    private LocalDateTime dataEnvio = LocalDateTime.now();
}


