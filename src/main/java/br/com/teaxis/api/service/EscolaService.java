package br.com.teaxis.api.service;

import br.com.teaxis.api.dto.EscolaDTO;
import br.com.teaxis.api.model.Escola;
import br.com.teaxis.api.repository.EscolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EscolaService {

    @Autowired
    private EscolaRepository escolaRepository;


    public Escola salvar(EscolaDTO dto) {
        Escola escola = new Escola();
        escola.setNome(dto.getNome());
        escola.setCidade(dto.getCidade());
        escola.setEstado(dto.getEstado());
        escola.setContato(dto.getContato());

        return escolaRepository.save(escola);
    }

    public List<Escola> listarTodas() {
        return escolaRepository.findAll();
    }

    public Escola buscarPorId(Long id) {
        return escolaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escola não encontrada com ID: " + id));
    }
    
    public void deletar(Long id) {
        escolaRepository.deleteById(id);
    }
}