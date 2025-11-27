package br.com.teaxis.api.dto;

import lombok.Data;

@Data
public class EscolaDTO {
    private String nome;
    private String cidade;
    private String estado;
    private String contato;
}