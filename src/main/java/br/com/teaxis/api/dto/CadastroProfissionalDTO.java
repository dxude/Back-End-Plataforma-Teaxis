package br.com.teaxis.api.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CadastroProfissionalDTO {

    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private String cidade;
    private String estado;
    
    private String certificacoes;   
    private String especializacoes;   
    private String metodosUtilizados; 
    private String disponibilidade;   
}