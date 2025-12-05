package br.com.teaxis.api.dto;
import br.com.teaxis.api.model.Profissional;
public record ProfissionalResponseDTO(

    Long id,
    String nome,
    String email,
    Double avaliacaoMedia,
    String especializacoes,    
    String metodosUtilizados  

) {

    public ProfissionalResponseDTO(Profissional profissional) {

        this(

            profissional.getId(),
            profissional.getUsuario().getNome(),
            profissional.getUsuario().getEmail(),
            profissional.getAvaliacaoMedia(),
            profissional.getEspecializacoes(),
            profissional.getMetodosUtilizados()

        );

    }

}