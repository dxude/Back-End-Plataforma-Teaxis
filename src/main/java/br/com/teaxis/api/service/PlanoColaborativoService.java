package br.com.teaxis.api.service;

import br.com.teaxis.api.dto.PlanoColaborativoDTO;
import br.com.teaxis.api.model.Escola;
import br.com.teaxis.api.model.PlanoColaborativo;
import br.com.teaxis.api.model.Profissional;
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.repository.EscolaRepository;
import br.com.teaxis.api.repository.PlanoColaborativoRepository;
import br.com.teaxis.api.repository.ProfissionalRepository; 
import br.com.teaxis.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanoColaborativoService {

    @Autowired
    private PlanoColaborativoRepository planoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ProfissionalRepository profissionalRepository;
    
    @Autowired
    private EscolaRepository escolaRepository;

    public PlanoColaborativo criarPlano(PlanoColaborativoDTO dto) {
        PlanoColaborativo plano = new PlanoColaborativo();

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Profissional profissional = profissionalRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        
        if (dto.getEscolaId() != null) {
            Escola escola = escolaRepository.findById(dto.getEscolaId())
                    .orElseThrow(() -> new RuntimeException("Escola não encontrada"));
            plano.setEscola(escola);
        }

        plano.setUsuario(usuario);
        plano.setProfissional(profissional);
        plano.setObjetivoGeral(dto.getObjetivoGeral());
        plano.setObservacoesCompartilhadas(dto.getObservacoesCompartilhadas());

        return planoRepository.save(plano);
    }

    public List<PlanoColaborativo> listarTodos() {
        return planoRepository.findAll();
    }
    
    public PlanoColaborativo buscarPorId(Long id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));
    }
}