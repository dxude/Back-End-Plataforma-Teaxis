package br.com.teaxis.api.service;

import br.com.teaxis.api.dto.CadastroProfissionalDTO;
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    }

    @Transactional
    public void cadastrarProfissional(CadastroProfissionalDTO dto) {
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
        
        profissionalRepository.save(novoProfissional);
    }
}