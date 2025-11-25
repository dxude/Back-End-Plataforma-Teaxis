package seu.pacote.models;

import jakarta.persistence.*;

@Entity
@Table(name = "metas")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idUsuario;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private String status; // pendente, em_progresso, concluida

    public Meta() {}

    public Meta(Long idUsuario, String titulo, String descricao, String status) {
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
    }

    // Getters e Setters ...

}
