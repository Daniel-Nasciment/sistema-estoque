package br.com.estoque.sistema_estoque.service;

import br.com.estoque.sistema_estoque.exception.ValidationException;
import br.com.estoque.sistema_estoque.model.Estoque;
import br.com.estoque.sistema_estoque.model.Historico;
import br.com.estoque.sistema_estoque.repository.EstoqueRepository;
import br.com.estoque.sistema_estoque.repository.HistoricoRepository;
import br.com.estoque.sistema_estoque.request.EstoqueRequest;
import br.com.estoque.sistema_estoque.request.EntradaEstoqueRequest;
import br.com.estoque.sistema_estoque.response.EstoqueResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final HistoricoRepository historicoRepository;

    public EstoqueResponse registrarEstoque(EstoqueRequest request) {
        Estoque estoque = estoqueRepository.save(request.toModel());
        return buildEstoqueResponse(estoque);
    }

    private EstoqueResponse buildEstoqueResponse(Estoque estoque) {
        return new EstoqueResponse(estoque.getId(), estoque.getCodigoProduto(), estoque.getQuantidadeDisponivel(), estoque.getCodigoBarras());
    }

    public void registrarEntradaEstoque(EntradaEstoqueRequest request) throws ValidationException {
        Estoque estoque = findByEstoqueId(request.getEstoqueId());
        Long novaQuantidadeDisponivel = estoque.getQuantidadeDisponivel() + request.getQuantidade();
        Historico historico = buildHistorico(estoque, novaQuantidadeDisponivel, request.getTipoEntrada().name());
        estoque.atualizarQuantidade(novaQuantidadeDisponivel);
        historicoRepository.save(historico);
        estoqueRepository.save(estoque);
    }

    private Historico buildHistorico(Estoque estoque, Long quantidade, String tipoMovimentacao) {
        Historico historico = Historico.builder()
                .estoque(estoque)
                .antigaQuantidade(estoque.getQuantidadeDisponivel())
                .quantidade(quantidade)
                .tipoMovimentacao(tipoMovimentacao)
                .build();

        estoque.addHistorico(historico);
        return historico;
    }

    private Estoque findByEstoqueId(Long estoqueId) throws ValidationException {
        return estoqueRepository.findById(estoqueId).orElseThrow(() -> new ValidationException("Estoque não encontrado!"));
    }
}
