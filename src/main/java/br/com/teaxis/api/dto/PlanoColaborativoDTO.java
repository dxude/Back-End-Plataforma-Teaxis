package br.com.teaxis.api.dto;

import lombok.Data;

@Data
public class PlanoColaborativoDTO {

    private Long usuarioId;
    private Long profissionalId;
    private Long escolaId;

    
    private String objetivoGeral;
    private String observacoesCompartilhadas;
}