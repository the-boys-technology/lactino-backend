package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.request.AtualizarLaticinioDTO;
import br.com.tbt.lactino.controller.request.LaticinioDTO;
import br.com.tbt.lactino.controller.request.LaticinioFiltro;
import br.com.tbt.lactino.controller.response.LaticinioDetalhadoResponse;
import br.com.tbt.lactino.model.Laticinio;
import br.com.tbt.lactino.model.Leite;
import br.com.tbt.lactino.model.Notificacao;
import br.com.tbt.lactino.model.Usuario;
import br.com.tbt.lactino.model.enums.StatusLaticinioEnum;
import br.com.tbt.lactino.model.enums.TipoNotificacao;
import br.com.tbt.lactino.repository.LaticinioRepository;
import br.com.tbt.lactino.repository.LeiteRepository;
import br.com.tbt.lactino.repository.NotificacaoRepository;
import br.com.tbt.lactino.repository.UsuarioRepository;
import br.com.tbt.lactino.repository.specifications.LaticinioSpecification;
import br.com.tbt.lactino.service.LaticinioService;
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
public class LaticinioServiceImpl implements LaticinioService {

    private final LaticinioRepository laticinioRepository;
    private final LeiteRepository leiteRepository;
    private final UsuarioRepository usuarioRepository;
    private final NotificacaoRepository notificacaoRepository;

    public LaticinioServiceImpl(LaticinioRepository laticinioRepository, LeiteRepository leiteRepository, UsuarioRepository usuarioRepository, NotificacaoRepository notificacaoRepository) {
        this.laticinioRepository = laticinioRepository;
        this.leiteRepository = leiteRepository;
        this.usuarioRepository = usuarioRepository;
        this.notificacaoRepository = notificacaoRepository;
    }

    @Override
    public UUID salvarLaticinio(String email, LaticinioDTO laticinioDTO) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        Leite leite = leiteRepository.findById(laticinioDTO.leiteUtilizadoId())
                .orElseThrow(() -> new EntityNotFoundException("Leite com ID " + laticinioDTO.leiteUtilizadoId() + " não encontrado."));

        Laticinio laticinio = laticinioDTO.toEntity(leite, usuario);
        laticinioRepository.save(laticinio);
        return laticinio.getId();
    }

    @Override
    public LaticinioDetalhadoResponse buscarLaticinio(UUID id) {
        Laticinio laticinio = laticinioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Laticínio com ID " + id + " não encontrado."));
        return new LaticinioDetalhadoResponse(laticinio);
    }

    @Override
    public Page<LaticinioDetalhadoResponse> listarLaticinios(LaticinioFiltro filtro, Pageable pageable) {
        Specification<Laticinio> spec = LaticinioSpecification.filtrar(filtro);
        Page<Laticinio> laticinios = laticinioRepository.findAll(spec, pageable);
        return laticinios.map(LaticinioDetalhadoResponse::new);
    }

    @Override
    public LaticinioDetalhadoResponse atualizarLaticinio(UUID id, AtualizarLaticinioDTO laticinioDTO) {
        Laticinio laticinio = laticinioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Laticínio com ID " + id + " não encontrado."));

        if (laticinioDTO.tipoProduto() != null) laticinio.setTipoProduto(laticinioDTO.tipoProduto());
        if (laticinioDTO.quantidadeProduzida() != null) laticinio.setQuantidadeProduzida(laticinioDTO.quantidadeProduzida());
        if (laticinioDTO.dataProducao() != null) {
            laticinio.setDataProducao(laticinioDTO.dataProducao());
            laticinio.setDataValidade(null);
            laticinio.calcularValidade();
        }
        if (laticinioDTO.descricao() != null) laticinio.setDescricao(laticinioDTO.descricao());
        if (laticinioDTO.status() != null) laticinio.setStatus(laticinioDTO.status());

        if (laticinioDTO.leiteUtilizadoId() != null) {
            Leite leite = leiteRepository.findById(laticinioDTO.leiteUtilizadoId())
                    .orElseThrow(() -> new EntityNotFoundException("Leite com ID " + laticinioDTO.leiteUtilizadoId() + " não encontrado."));
            laticinio.setLeiteUtilizado(leite);
        }

        laticinioRepository.save(laticinio);
        return new LaticinioDetalhadoResponse(laticinio);
    }

    @Override
    public List<LaticinioDetalhadoResponse> listarLaticiniosVencendo(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(3);

        List<Laticinio> laticinios = laticinioRepository
                .findByUsuarioAndStatusAndDataValidadeBetween(usuario, StatusLaticinioEnum.EM_ESTOQUE, hoje, limite);

        return laticinios.stream()
                .map(LaticinioDetalhadoResponse::new)
                .toList();
    }

    @Override
    public void removerLaticinio(UUID id) {
        Laticinio laticinio = laticinioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Laticínio com ID " + id + " não encontrado"));

        laticinioRepository.delete(laticinio);
    }

    @Scheduled(fixedDelay = 300000) // 5 minutos
    public void atualizarStatusLaticinios() {
        System.out.print("EXECUTANDO: atualizarStatusLaticinios");
        LocalDate hoje = LocalDate.now();

        List<Laticinio> laticiniosEmEstoque = laticinioRepository.findByStatus(StatusLaticinioEnum.EM_ESTOQUE);

        List<Laticinio> laticiniosVencidos = laticiniosEmEstoque.stream()
                .filter(l -> l.getDataValidade().isBefore(hoje))
                .peek(l -> l.setStatus(StatusLaticinioEnum.VENCIDO))
                .toList();

        if (!laticiniosVencidos.isEmpty()) {
            laticinioRepository.saveAll(laticiniosVencidos);
            System.out.println("Laticínios atualizados como VENCIDO: " + laticiniosVencidos.size());
        }

        List<Notificacao> notificacoes = laticiniosVencidos.stream()
                .map(laticinio -> Notificacao.builder()
                        .usuario(laticinio.getUsuario())
                        .titulo("Leite vencido")
                        .mensagem("O laticínio '" + laticinio.getTipoProduto() + "' venceu em " + laticinio.getDataValidade())
                        .tipoNotificacao(TipoNotificacao.VENCIMENTO_LATICINIO)
                        .lida(false)
                        .criadaEm(LocalDateTime.now())
                        .build())
                .toList();

        notificacaoRepository.saveAll(notificacoes);
        System.out.println("Notificações de laticínios vencido enviadas: " + notificacoes.size());
    }
}
