package br.com.teaxis.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "profissionais")
@EqualsAndHashCode(of = "id") // Evita problemas em comparações e loops
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento crucial com Usuario. Garanta que Usuario.java não tenha erros.
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Column(columnDefinition = "TEXT")
    private String disponibilidade;

    private Double avaliacaoMedia = 0.0;

    // Usando Set<String> para as listas
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profissional_certificacoes", joinColumns = @JoinColumn(name = "profissional_id"))
    @Column(name = "certificacao", columnDefinition="TEXT")
    private Set<String> certificacoes = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profissional_especializacoes", joinColumns = @JoinColumn(name = "profissional_id"))
    @Column(name = "especializacao")
    private Set<String> especializacoes = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profissional_metodos", joinColumns = @JoinColumn(name = "profissional_id"))
    @Column(name = "metodo")
    private Set<String> metodosUtilizados = new HashSet<>();
    
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profissional_hobbies", joinColumns = @JoinColumn(name = "profissional_id"))
    @Column(name = "hobby")
    private Set<String> hobbies = new HashSet<>();
}