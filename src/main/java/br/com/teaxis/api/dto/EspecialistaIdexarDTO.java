package br.com.teaxis.api.dto;

public record EspecialistaIdexarDTO(
    Long id_profissional, 
    String nome, 
    String texto_clinico
) {}