package br.com.teaxis.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensagens")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campos que o código antigo do seu grupo exige:
    @Column(name = "remetente_id")
    private Long remetenteId;

    @Column(name = "destinatario_id")
    private Long destinatarioId;

    // Campos que o front-end e a tabela do Neon precisam:
    @Column(name = "remetente_email")
    private String remetenteEmail;

    @Column(name = "destinatario_email")
    private String destinatarioEmail;

    private String assunto;
    
    @Column(columnDefinition = "TEXT")
    private String conteudo;
    
    @Column(name = "data_envio")
    private LocalDateTime dataEnvio = LocalDateTime.now();

    // --- CONSTRUTORES ---
    public Mensagem() {}

    // --- GETTERS E SETTERS (O que o Maven precisa para compilar) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRemetenteId() { return remetenteId; }
    public void setRemetenteId(Long remetenteId) { this.remetenteId = remetenteId; }

    public Long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }

    public String getRemetenteEmail() { return remetenteEmail; }
    public void setRemetenteEmail(String remetenteEmail) { this.remetenteEmail = remetenteEmail; }

    public String getDestinatarioEmail() { return destinatarioEmail; }
    public void setDestinatarioEmail(String destinatarioEmail) { this.destinatarioEmail = destinatarioEmail; }

    public String getAssunto() { return assunto; }
    public void setAssunto(String assunto) { this.assunto = assunto; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }
}