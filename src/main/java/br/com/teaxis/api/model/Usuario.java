package br.com.teaxis.api.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;


    @Enumerated(EnumType.STRING) 
    private TipoUsuario tipo; 

    private LocalDate dataNascimento;
    private String genero;
    private String cidade;
    private String estado;
    private String tipoNeurodivergencia;

    @Column(columnDefinition = "TEXT")
    private String preferenciasSensoriais;

    @Column(columnDefinition = "TEXT")
    private String modoComunicacao;

    @Column(columnDefinition = "TEXT")
    private String historicoEscolar;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_hobbies", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "hobby")
    private Set<String> hobbies;
    
    @OneToMany(mappedBy = "usuario")
    private List<Avaliacao> avaliacoesFeitas;

    @OneToMany(mappedBy = "usuario")
    private List<Favorito> favoritos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.tipo == null) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER")); 
        }

        return List.of(new SimpleGrantedAuthority("ROLE_" + this.tipo.name()));
         }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; 
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; 
    }

    @Override
    public boolean isEnabled() {
        return true; 
    }
}