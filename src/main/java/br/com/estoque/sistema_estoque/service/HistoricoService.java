package br.com.estoque.sistema_estoque.service;

import br.com.estoque.sistema_estoque.model.Estoque;
import br.com.estoque.sistema_estoque.model.Historico;
import br.com.estoque.sistema_estoque.repository.HistoricoRepository;
import br.com.estoque.sistema_estoque.response.HistoricoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoricoService {

    private final HistoricoRepository historicoRepository;

    public void saveHistorico(Historico historico){
        log.debug("Salvando historico. Historico: {}", historico);
        this.historicoRepository.save(historico);
        log.debug("Historico salvo com sucesso.");
    }

    public Historico criarHistoricoMovimentacao(String tipoMovimentacao, Estoque estoque, Long quantidadeDisponivel) {
        log.debug("Criando histórico de movimentação. Tipo: {}, Estoque: {}, Quantidade Disponível: {}", tipoMovimentacao, estoque, quantidadeDisponivel);
        Historico historico = buildHistorico(estoque, quantidadeDisponivel, tipoMovimentacao);
        estoque.atualizarQuantidade(quantidadeDisponivel);
        return historico;
    }

    public Historico buildHistorico(Estoque estoque, Long quantidade, String tipoMovimentacao) {
        log.debug("Construindo histórico. Estoque: {}, Quantidade: {}, Tipo Movimentação: {}", estoque, quantidade, tipoMovimentacao);
        Historico historico = new Historico(estoque, quantidade, tipoMovimentacao);
        estoque.addHistorico(historico);
        return historico;
    }

    public Page<?> findAllHistorico(Pageable pageable, Long estoqueId) {
        return historicoRepository.findByEstoqueIdOrderByCriadoEmDesc(pageable, estoqueId).map(this::buildResponse);
    }

    private HistoricoResponse buildResponse(Historico historico) {
        Estoque estoque = historico.getEstoque();
        Long estoqueId = estoque.getId();
        String codigoProduto = estoque.getCodigoProduto();
        Long codigoBarras = estoque.getCodigoBarras();
        return new HistoricoResponse(estoqueId, codigoProduto, codigoBarras, historico.getTipoMovimentacao(), historico.getNovaQuantidade(), historico.getAntigaQuantidade(), historico.getCriadoEm());
    }
}
