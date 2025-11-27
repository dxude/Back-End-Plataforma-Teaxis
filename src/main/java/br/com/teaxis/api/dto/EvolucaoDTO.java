package br.com.teaxis.api.dto;

import lombok.Data; 
import java.time.LocalDate;

@Data
public class EvolucaoDTO {

    private Long metaId;
    private LocalDate dataRegistro;
    private String progresso;
    private String comentario;
}