package br.com.teaxis.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensagens")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "remetente_email")
    private String remetenteEmail;

    @Column(name = "destinatario_email")
    private String destinatarioEmail;

    private String assunto;
    
    @Column(columnDefinition = "TEXT")
    private String conteudo;
    
    @Column(name = "data_envio")
    private LocalDateTime dataEnvio = LocalDateTime.now();

    // Getters, Setters e Construtores
}