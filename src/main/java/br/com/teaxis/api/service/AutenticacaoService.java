package br.com.teaxis.api.service;

import br.com.teaxis.api.dto.CadastroProfissionalDTO;
import br.com.teaxis.api.dto.ProfissionalResponseDTO; 
import br.com.teaxis.api.model.Profissional;
import br.com.teaxis.api.model.TipoUsuario;
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.repository.ProfissionalRepository;
import br.com.teaxis.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder; 

    // INJEÇÃO DA IA: Adicionando o serviço de matching
    @Autowired
    private MatchingService matchingService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    }

    @Transactional
    public ProfissionalResponseDTO cadastrarProfissional(CadastroProfissionalDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
             throw new RuntimeException("Email já cadastrado!");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.getNome());
        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(dto.getSenha())); 
        novoUsuario.setTipo(TipoUsuario.PROFISSIONAL); 
        novoUsuario.setCidade(dto.getCidade());
        novoUsuario.setEstado(dto.getEstado());
        novoUsuario.setDataNascimento(dto.getDataNascimento());
        
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        Profissional novoProfissional = new Profissional();
        novoProfissional.setUsuario(usuarioSalvo);
        novoProfissional.setCertificacoes(dto.getCertificacoes());
        novoProfissional.setEspecializacoes(dto.getEspecializacoes());
        novoProfissional.setMetodosUtilizados(dto.getMetodosUtilizados());
        novoProfissional.setDisponibilidade(dto.getDisponibilidade());
        
        // Salva o profissional no banco de dados relacional
        Profissional profissionalSalvo = profissionalRepository.save(novoProfissional);
        
        // AMARRAÇÃO COM O BANCO VETORIAL (IA)
        try {
            matchingService.indexarProfissionalNoBancoVetorial(profissionalSalvo);
        } catch (Exception e) {
            // Um bloco try-catch garante que se o microsserviço Python estiver offline, 
            // o cadastro principal do profissional na plataforma ainda funcione com sucesso.
            System.err.println("Aviso: Não foi possível enviar os dados do profissional para o banco vetorial. Erro: " + e.getMessage());
        }
        
        return new ProfissionalResponseDTO(profissionalSalvo);
    }
}