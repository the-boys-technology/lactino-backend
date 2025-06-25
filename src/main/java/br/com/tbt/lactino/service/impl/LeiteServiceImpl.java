package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.AtualizarLeiteDTO;
import br.com.tbt.lactino.controller.request.LeiteDTO;
import br.com.tbt.lactino.controller.request.LeiteFiltro;
import br.com.tbt.lactino.controller.response.LeiteDetalhadoResponse;
import br.com.tbt.lactino.model.Leite;
import br.com.tbt.lactino.model.Notificacao;
import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.model.enums.StatusLeiteEnum;
import br.com.tbt.lactino.model.enums.TipoNotificacao;
import br.com.tbt.lactino.repository.LeiteRepository;
import br.com.tbt.lactino.repository.NotificacaoRepository;
import br.com.tbt.lactino.repository.UsuarioRepository;
import br.com.tbt.lactino.repository.specifications.LeiteSpecification;
import br.com.tbt.lactino.service.LeiteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LeiteServiceImpl implements LeiteService {

    private final LeiteRepository leiteRepository;
    private final UsuarioRepository usuarioRepository;
    private final NotificacaoRepository notificacaoRepository;

    public LeiteServiceImpl(LeiteRepository leiteRepository, UsuarioRepository usuarioRepository, NotificacaoRepository notificacaoRepository) {
        this.leiteRepository = leiteRepository;
        this.usuarioRepository = usuarioRepository;
        this.notificacaoRepository = notificacaoRepository;
    }

    @Override
    public UUID salvarLeite(String email, LeiteDTO leiteDTO) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        Leite leite = leiteDTO.toEntity(usuario);
        leiteRepository.save(leite);
        return leite.getId();
    }

    @Override
    public LeiteDetalhadoResponse buscarLeite(UUID leiteId) {
        return leiteRepository.findById(leiteId)
                .map(LeiteDetalhadoResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("Leite com ID " + leiteId + " não encontrado."));
    }

    @Override
    public LeiteDetalhadoResponse atualizarLeite(UUID id, AtualizarLeiteDTO leiteDTO) {
        Leite leite = leiteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Leite com ID " + id + " não encontrado."));

        if (leiteDTO.nome() != null) leite.setNome(leiteDTO.nome());
        if (leiteDTO.descricao() != null) leite.setDescricao(leiteDTO.descricao());
        if (leiteDTO.dataObtencao() != null) {
            leite.setDataObtencao(leiteDTO.dataObtencao());
            leite.setDataValidade(leiteDTO.dataObtencao().plusDays(7));
        }
        if (leiteDTO.origem() != null) leite.setOrigem(leiteDTO.origem());
        if (leiteDTO.turno() != null) leite.setTurno(leiteDTO.turno());
        if (leiteDTO.status() != null) leite.setStatus(leiteDTO.status());
        if (leiteDTO.finalidade() != null) leite.setFinalidade(leiteDTO.finalidade());

        leiteRepository.save(leite);
        return new LeiteDetalhadoResponse(leite);
    }

    @Override
    public LeiteDetalhadoResponse transformarLeite(UUID leiteId) {
        Leite leite = leiteRepository.findById(leiteId)
                .orElseThrow(() -> new EntityNotFoundException("Leite com ID " + leiteId + " não encontrado."));

        leite.setStatus(StatusLeiteEnum.UTILIZADO);
        leiteRepository.save(leite);
        return new LeiteDetalhadoResponse(leite);
    }

    @Override
    public Page<LeiteDetalhadoResponse> listarLeitesComFiltro(LeiteFiltro filtro, Pageable pageable) {
        Specification<Leite> spec = LeiteSpecification.filtrar(filtro);
        Page<Leite> leites = leiteRepository.findAll(spec, pageable);
        return leites.map(LeiteDetalhadoResponse::new);
    }

    @Override
    public List<LeiteDetalhadoResponse> listarLeitesVencendo(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(3);

        List<Leite> leites = leiteRepository.findByUsuarioAndStatusAndDataValidadeLessThanEqual(
                usuario, StatusLeiteEnum.DISPONIVEL, limite);

        return leites.stream()
                .map(LeiteDetalhadoResponse::new)
                .toList();
    }

    @Override
    public void removerLeite(UUID leiteId) {
        Leite leite = leiteRepository.findById(leiteId)
                .orElseThrow(() -> new EntityNotFoundException("Leite com ID " + leiteId + " não encontrado"));
        leiteRepository.delete(leite);
    }

    @Scheduled(fixedDelay = 300000) // 5 minutos
    public void atualizarStatusLeite() {
        System.out.print("EXECUTANDO: atualizarStatusLeite");
        LocalDate hoje = LocalDate.now();

        List<Leite> leitesDisponiveis = leiteRepository.findByStatus(StatusLeiteEnum.DISPONIVEL);

        List<Leite> leitesVencidos = leitesDisponiveis.stream()
                .filter(leite -> leite.getDataValidade().isBefore(hoje))
                .peek(leite -> leite.setStatus(StatusLeiteEnum.VENCIDO))
                .toList();

        if (!leitesVencidos.isEmpty()) {
            leiteRepository.saveAll(leitesVencidos);
            System.out.println("Leites atualizados como VENCIDO: " + leitesVencidos.size());
        }

        List<Notificacao> notificacoes = leitesVencidos.stream()
                .map(leite -> Notificacao.builder()
                        .usuario(leite.getUsuario())
                        .titulo("Leite vencido")
                        .mensagem("O leite '" + leite.getNome() + "' venceu em " + leite.getDataValidade())
                        .tipoNotificacao(TipoNotificacao.VENCIMENTO_LEITE)
                        .lida(false)
                        .criadaEm(LocalDateTime.now())
                        .build())
                .toList();

        notificacaoRepository.saveAll(notificacoes);
        System.out.println("Notificações de leite vencido enviadas: " + notificacoes.size());
    }
}
