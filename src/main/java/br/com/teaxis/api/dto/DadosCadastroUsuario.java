package br.com.teaxis.api.dto;

import java.time.LocalDate;
import java.util.Set;

public record DadosCadastroUsuario(
    String nome,
    String email,
    String senha,
    LocalDate dataNascimento,
    String tipoNeurodivergencia,
    Set<String> hobbies,

    
    String tipo,
    String genero,
    String cidade,
    String estado,
    String preferenciasSensoriais,
    String modoComunicacao,
    String historicoEscolar
  
) {}