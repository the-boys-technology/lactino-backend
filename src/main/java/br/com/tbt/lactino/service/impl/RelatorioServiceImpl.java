package br.com.tbt.lactino.service.impl;

import br.com.tbt.lactino.controller.response.ItemRelatorioResponse;
import br.com.tbt.lactino.controller.response.RelatorioPedidoResponse;
import br.com.tbt.lactino.model.Cliente;
import br.com.tbt.lactino.model.Fornecedor;
import br.com.tbt.lactino.model.ItemTransacao;
import br.com.tbt.lactino.model.Transacao;
import br.com.tbt.lactino.model.enums.CategoriaProduto;
import br.com.tbt.lactino.repository.*;
import br.com.tbt.lactino.service.RelatorioService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RelatorioServiceImpl implements RelatorioService {

    private final TransacaoRepository transacaoRepository;
    private final ItemTransacaoRepository itemTransacaoRepository;
    private final LeiteRepository leiteRepository;
    private final LaticinioRepository laticinioRepository;
    private final InsumoRepository insumoRepository;

    public RelatorioServiceImpl(
            TransacaoRepository transacaoRepository,
            ItemTransacaoRepository itemTransacaoRepository,
            LeiteRepository leiteRepository,
            LaticinioRepository laticinioRepository,
            InsumoRepository insumoRepository
    ) {
        this.transacaoRepository = transacaoRepository;
        this.itemTransacaoRepository = itemTransacaoRepository;
        this.leiteRepository = leiteRepository;
        this.laticinioRepository = laticinioRepository;
        this.insumoRepository = insumoRepository;
    }

    @Override
    public RelatorioPedidoResponse gerarRelatorioPedido(Long transacaoId) {
        Transacao transacao = transacaoRepository.findById(transacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));

        Cliente cliente = transacao.getCliente();
        Fornecedor fornecedor = transacao.getFornecedor();

        List<ItemTransacao> itens = itemTransacaoRepository.findByTransacaoId(transacaoId);

        List<ItemRelatorioResponse> itensRelatorio = itens.stream()
                .map(item -> {
                    String nomeProduto = buscarNomeProduto(item.getCategoria(), item.getProdutoId());

                    BigDecimal subtotal = item.getPrecoUnitario()
                            .multiply(BigDecimal.valueOf(item.getQuantidade()));

                    return new ItemRelatorioResponse(
                            nomeProduto,
                            item.getCategoria().name(),
                            item.getQuantidade(),
                            item.getPrecoUnitario(),
                            subtotal
                    );
                })
                .collect(Collectors.toList());

        return new RelatorioPedidoResponse(
                transacao.getId(),
                transacao.getData(),
                cliente != null ? cliente.getNome() : "N/A",
                cliente != null ? cliente.getEmail() : "N/A",
                cliente != null ? cliente.getLocalizacao() : "N/A",
                fornecedor != null ? fornecedor.getNome() : "N/A",
                fornecedor != null ? fornecedor.getEmail() : "N/A",
                fornecedor != null ? fornecedor.getLocalizacao() : "N/A",
                transacao.getFormaPagamento().name(),
                transacao.getValorTotal(),
                transacao.getDescricao(),
                itensRelatorio
        );
    }

    @Override
    public byte[] gerarRelatorioPedidoPdf(Long transacaoId) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, baos);
            document.open();

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 11);

            Paragraph titulo = new Paragraph("Relatório do Pedido", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            RelatorioPedidoResponse relatorio = gerarRelatorioPedido(transacaoId);

            document.add(new Paragraph("ID do Pedido: " + relatorio.transacaoId(), fontNormal));
            document.add(new Paragraph("Data do Pedido: " + relatorio.data().format(formatter), fontNormal));
            document.add(new Paragraph("Forma de Pagamento: " + relatorio.formaPagamento(), fontNormal));
            if (relatorio.descricao() != null && !relatorio.descricao().isBlank()) {
                document.add(new Paragraph("Descrição: " + relatorio.descricao(), fontNormal));
            }

            document.add(Chunk.NEWLINE);

            Paragraph clienteTitulo = new Paragraph("Dados do Cliente", fontSubtitulo);
            clienteTitulo.setSpacingAfter(8);
            document.add(clienteTitulo);

            PdfPTable tabelaCliente = new PdfPTable(2);
            tabelaCliente.setWidthPercentage(100);
            tabelaCliente.setWidths(new float[]{1f, 3f});
            tabelaCliente.addCell(celulaNegrito("Nome"));
            tabelaCliente.addCell(celulaNormal(relatorio.clienteNome()));
            tabelaCliente.addCell(celulaNegrito("Email"));
            tabelaCliente.addCell(celulaNormal(relatorio.clienteEmail()));
            tabelaCliente.addCell(celulaNegrito("Localização"));
            tabelaCliente.addCell(celulaNormal(relatorio.clienteLocalizacao()));
            document.add(tabelaCliente);

            document.add(Chunk.NEWLINE);

            Paragraph fornecedorTitulo = new Paragraph("Dados do Fornecedor", fontSubtitulo);
            fornecedorTitulo.setSpacingAfter(8);
            document.add(fornecedorTitulo);

            PdfPTable tabelaFornecedor = new PdfPTable(2);
            tabelaFornecedor.setWidthPercentage(100);
            tabelaFornecedor.setWidths(new float[]{1f, 3f});
            tabelaFornecedor.addCell(celulaNegrito("Nome"));
            tabelaFornecedor.addCell(celulaNormal(relatorio.fornecedorNome()));
            tabelaFornecedor.addCell(celulaNegrito("Email"));
            tabelaFornecedor.addCell(celulaNormal(relatorio.fornecedorEmail()));
            tabelaFornecedor.addCell(celulaNegrito("Localização"));
            tabelaFornecedor.addCell(celulaNormal(relatorio.fornecedorLocalizacao()));
            document.add(tabelaFornecedor);

            document.add(Chunk.NEWLINE);

            Paragraph itensTitulo = new Paragraph("Itens do Pedido", fontSubtitulo);
            itensTitulo.setSpacingAfter(8);
            document.add(itensTitulo);

            PdfPTable tabelaItens = new PdfPTable(5);
            tabelaItens.setWidthPercentage(100);
            tabelaItens.setWidths(new float[]{4f, 2.5f, 1.5f, 2.5f, 2.5f});

            Stream.of("Produto", "Categoria", "Qtd.", "Preço Unitário", "Subtotal")
                    .forEach(header -> {
                        PdfPCell cell = new PdfPCell(new Phrase(header, fontSubtitulo));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBackgroundColor(new Color(230, 230, 230));
                        tabelaItens.addCell(cell);
                    });

            for (ItemRelatorioResponse item : relatorio.itens()) {
                tabelaItens.addCell(celulaNormal(item.nomeProduto()));
                tabelaItens.addCell(celulaNormal(item.categoria()));
                tabelaItens.addCell(celulaNormal(String.valueOf(item.quantidade())));
                tabelaItens.addCell(celulaNormal("R$ " + item.precoUnitario().setScale(2)));
                tabelaItens.addCell(celulaNormal("R$ " + item.subtotal().setScale(2)));
            }
            document.add(tabelaItens);

            document.add(Chunk.NEWLINE);

            Paragraph total = new Paragraph("Total do Pedido: R$ " + relatorio.valorTotal().setScale(2), fontSubtitulo);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();
            return baos.toByteArray();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

    private PdfPCell celulaNegrito(String texto) {
        Font fontNegrito = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        PdfPCell cell = new PdfPCell(new Phrase(texto, fontNegrito));
        cell.setPadding(5);
        return cell;
    }

    private PdfPCell celulaNormal(String texto) {
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 11);
        PdfPCell cell = new PdfPCell(new Phrase(texto, fontNormal));
        cell.setPadding(5);
        return cell;
    }

    private String buscarNomeProduto(CategoriaProduto categoria, UUID produtoId) {
        return switch (categoria) {
            case LEITE -> leiteRepository.findById(produtoId)
                    .orElseThrow(() -> new EntityNotFoundException("Leite não encontrado"))
                    .getNome();

            case LATICINIO -> laticinioRepository.findById(produtoId)
                    .orElseThrow(() -> new EntityNotFoundException("Laticínio não encontrado"))
                    .getTipoProduto();

            case INSUMO -> insumoRepository.findById(produtoId)
                    .orElseThrow(() -> new EntityNotFoundException("Insumo não encontrado"))
                    .getNome();
        };
    }
}
