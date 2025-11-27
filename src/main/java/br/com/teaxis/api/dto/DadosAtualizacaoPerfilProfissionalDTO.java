package br.com.teaxis.api.dto;


public record DadosAtualizacaoPerfilProfissionalDTO(
    String disponibilidade,
    String certificacoes,      
    String especializacoes,    
    String metodosUtilizados,  
    String hobbies            
) {}