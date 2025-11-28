package br.com.teaxis.api.model;

import jakarta.persistence.*;
import lombok.Data;

//Entidade JPA que representa uma notificação enviada a um usuário.

@Entity
@Table(name = "notificacoes")
@Data
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    @Column(nullable = false)
    private String mensagem;

    private boolean lido = false;
}

