package br.com.teaxis.api.service;

import br.com.teaxis.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Esta é a única linha necessária dentro do método.
        // Ela busca o usuário ou lança uma exceção se não o encontrar.
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Dados de login inválidos!"));
    }
}