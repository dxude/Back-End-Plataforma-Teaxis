package br.com.teaxis.api.service;

import br.com.teaxis.api.dto.DadosAtualizacaoUsuario;
import br.com.teaxis.api.dto.DadosCadastroUsuario;
import br.com.teaxis.api.dto.UsuarioResponseDTO;
import br.com.teaxis.api.exception.ValidacaoException;
import br.com.teaxis.api.model.TipoUsuario;
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional 
    public Usuario cadastrarUsuario(DadosCadastroUsuario dados) {
        if (usuarioRepository.findByEmail(dados.email()).isPresent()) {
            throw new ValidacaoException("Email já cadastrado!");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dados.nome());
        novoUsuario.setEmail(dados.email());
        novoUsuario.setSenha(passwordEncoder.encode(dados.senha()));

        
        if (dados.tipo() != null && !dados.tipo().isBlank()) {
            try {
        
                novoUsuario.setTipo(TipoUsuario.valueOf(dados.tipo().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new ValidacaoException("Tipo de usuário inválido! Use: USUARIO, PROFISSIONAL ou FAMILIA");
            }
        } else {
            
            novoUsuario.setTipo(TipoUsuario.USUARIO);
        }
        

        novoUsuario.setDataNascimento(dados.dataNascimento());
        novoUsuario.setGenero(dados.genero());
        novoUsuario.setCidade(dados.cidade());
        novoUsuario.setEstado(dados.estado());
        novoUsuario.setTipoNeurodivergencia(dados.tipoNeurodivergencia());
        novoUsuario.setPreferenciasSensoriais(dados.preferenciasSensoriais());
        novoUsuario.setModoComunicacao(dados.modoComunicacao());
        novoUsuario.setHistoricoEscolar(dados.historicoEscolar());
        novoUsuario.setHobbies(dados.hobbies());

        return usuarioRepository.save(novoUsuario);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + id));
        return new UsuarioResponseDTO(usuario);
    }

    @Transactional 
    public UsuarioResponseDTO atualizarUsuario(Long id, DadosAtualizacaoUsuario dados) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + id));

        if (dados.nome() != null) {
            usuario.setNome(dados.nome());
        }
        if (dados.cidade() != null) {
            usuario.setCidade(dados.cidade());
        }
        if (dados.estado() != null) {
            usuario.setEstado(dados.estado());
        }
        if (dados.hobbies() != null) {
            usuario.setHobbies(dados.hobbies());
        }
        
        usuarioRepository.save(usuario);
        

        return new UsuarioResponseDTO(usuario);
    }

    public void excluirUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}