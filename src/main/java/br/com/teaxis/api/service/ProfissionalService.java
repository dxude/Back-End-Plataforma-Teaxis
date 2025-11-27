package br.com.teaxis.api.service;

import br.com.teaxis.api.dto.DadosAtualizacaoPerfilProfissionalDTO;
import br.com.teaxis.api.dto.ProfissionalResponseDTO;
import br.com.teaxis.api.model.Profissional;
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.repository.ProfissionalRepository;
import br.com.teaxis.api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; 

    @Transactional
    public ProfissionalResponseDTO atualizarOuCriarPerfil(Usuario usuarioLogado, DadosAtualizacaoPerfilProfissionalDTO dados) {
        Profissional profissional = profissionalRepository.findByUsuarioId(usuarioLogado.getId())
                .orElseGet(() -> {
                    Usuario usuarioParaPersistir = usuarioRepository.findById(usuarioLogado.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado (ID: " + usuarioLogado.getId() + ") ao tentar criar perfil profissional."));
                    
                    Profissional novoProfissional = new Profissional();
                    novoProfissional.setUsuario(usuarioParaPersistir); 
                    return novoProfissional;
                });

        if (dados.disponibilidade() != null) {
            profissional.setDisponibilidade(dados.disponibilidade());
        }
        if (dados.certificacoes() != null) {
            profissional.setCertificacoes(dados.certificacoes());
        }
        if (dados.especializacoes() != null) {
            profissional.setEspecializacoes(dados.especializacoes());
        }
        if (dados.metodosUtilizados() != null) {
            profissional.setMetodosUtilizados(dados.metodosUtilizados());
        }
        if (dados.hobbies() != null) {
            profissional.setHobbies(dados.hobbies());
        }

        Profissional profissionalSalvo = profissionalRepository.save(profissional);
        return new ProfissionalResponseDTO(profissionalSalvo);
    }

    public List<ProfissionalResponseDTO> listarTodos() {
        return profissionalRepository.findAll().stream()
                .map(ProfissionalResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ProfissionalResponseDTO buscarPorId(Long id) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado com ID: " + id));
        return new ProfissionalResponseDTO(profissional);
    }
}