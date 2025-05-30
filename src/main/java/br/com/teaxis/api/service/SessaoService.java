package br.com.teaxis.api.service;

import br.com.teaxis.api.dto.DadosAgendamentoSessaoDTO;
import br.com.teaxis.api.dto.DadosAtualizacaoStatusSessaoDTO;
import br.com.teaxis.api.dto.SessaoResponseDTO;
import br.com.teaxis.api.enums.StatusSessao;
import br.com.teaxis.api.model.Profissional;
import br.com.teaxis.api.model.Sessao;
import br.com.teaxis.api.model.Usuario;
import br.com.teaxis.api.repository.ProfissionalRepository;
import br.com.teaxis.api.repository.SessaoRepository;
import br.com.teaxis.api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; 

    public List<SessaoResponseDTO> listarSessoesParaProfissionalLogado(Usuario usuarioProfissionalLogado) {
        Profissional profissional = profissionalRepository.findByUsuarioId(usuarioProfissionalLogado.getId())
                .orElseThrow(() -> new EntityNotFoundException("Perfil profissional não encontrado para o usuário: " + usuarioProfissionalLogado.getEmail()));
        return sessaoRepository.findByProfissionalIdOrderByDataHoraAgendamentoDesc(profissional.getId()).stream()
                .map(SessaoResponseDTO::new)
                .collect(Collectors.toList());
    } 

    @Transactional
    public SessaoResponseDTO agendarSessao(Usuario usuarioLogado, DadosAgendamentoSessaoDTO dados) {
        Profissional profissional = profissionalRepository.findById(dados.profissionalId())
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado com ID: " + dados.profissionalId()));


        Sessao novaSessao = new Sessao();
        novaSessao.setUsuario(usuarioLogado); 
        novaSessao.setProfissional(profissional);
        novaSessao.setDataHoraAgendamento(dados.dataHoraAgendamento());
        novaSessao.setTipoAtendimento(dados.tipoAtendimento());
        novaSessao.setLocalOuLink(dados.localOuLink());
        novaSessao.setObservacoesUsuario(dados.observacoesUsuario());
        novaSessao.setDuracaoEstimadaMinutos(dados.duracaoEstimadaMinutos());
        novaSessao.setStatus(StatusSessao.AGENDADA); 

        Sessao sessaoSalva = sessaoRepository.save(novaSessao);
        return new SessaoResponseDTO(sessaoSalva);
    }

    public List<SessaoResponseDTO> listarSessoesPorUsuario(Long usuarioId) {
        return sessaoRepository.findByUsuarioIdOrderByDataHoraAgendamentoDesc(usuarioId).stream()
                .map(SessaoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<SessaoResponseDTO> listarSessoesPorProfissional(Long profissionalId) {
        return sessaoRepository.findByProfissionalIdOrderByDataHoraAgendamentoDesc(profissionalId).stream()
                .map(SessaoResponseDTO::new)
                .collect(Collectors.toList());
    }
    
    public SessaoResponseDTO buscarSessaoPorId(Long sessaoId) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada com ID: " + sessaoId));
        return new SessaoResponseDTO(sessao);
    }

    @Transactional
    public SessaoResponseDTO atualizarStatusSessao(Long sessaoId, DadosAtualizacaoStatusSessaoDTO dados, Usuario usuarioLogado) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada com ID: " + sessaoId));

        // Lógica de permissão: Quem pode mudar qual status?
        if (!sessao.getProfissional().getUsuario().getId().equals(usuarioLogado.getId()) &&
            !sessao.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new SecurityException("Usuário não autorizado a modificar esta sessão.");
        }

        sessao.setStatus(dados.novoStatus());
        if (dados.observacoes() != null && usuarioLogado.getId().equals(sessao.getProfissional().getUsuario().getId())) {
            // Só permite que o profissional adicione/altere observacoesProfissional
             String obsAtuais = sessao.getObservacoesProfissional() == null ? "" : sessao.getObservacoesProfissional() + "\n";
            sessao.setObservacoesProfissional(obsAtuais + "Status alterado para " + dados.novoStatus() + ": " + dados.observacoes());
        }

        Sessao sessaoAtualizada = sessaoRepository.save(sessao);
        return new SessaoResponseDTO(sessaoAtualizada);
    }
}