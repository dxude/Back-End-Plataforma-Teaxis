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

    public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public Long getIdUsuario() {
    return idUsuario;
}

public void setIdUsuario(Long idUsuario) {
    this.idUsuario = idUsuario;
}

public String getTitulo() {
    return titulo;
}

public void setTitulo(String titulo) {
    this.titulo = titulo;
}

public String getDescricao() {
    return descricao;
}

public void setDescricao(String descricao) {
    this.descricao = descricao;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}

}
