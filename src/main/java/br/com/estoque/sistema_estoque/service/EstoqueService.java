package br.com.estoque.sistema_estoque.service;

import br.com.estoque.sistema_estoque.exception.ValidationException;
import br.com.estoque.sistema_estoque.model.Estoque;
import br.com.estoque.sistema_estoque.model.Historico;
import br.com.estoque.sistema_estoque.repository.EstoqueRepository;
import br.com.estoque.sistema_estoque.repository.HistoricoRepository;
import br.com.estoque.sistema_estoque.request.CadastroEstoqueRequest;
import br.com.estoque.sistema_estoque.request.RegistrarEntradaRequest;
import br.com.estoque.sistema_estoque.request.RegistrarSaidaRequest;
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

    public EstoqueResponse cadastrarEstoque(CadastroEstoqueRequest request) {
        Estoque estoque = estoqueRepository.save(request.toModel());
        return buildEstoqueResponse(estoque);
    }

    public void registrarEntrada(RegistrarEntradaRequest request) throws ValidationException {
        Estoque estoque = findByEstoqueId(request.getEstoqueId());
        criarHistoricoMovimentacao(request.getTipoEntrada().name(), estoque, estoque.getQuantidadeDisponivel() + request.getQuantidade());
    }

    public void registrarSaida(RegistrarSaidaRequest request) throws ValidationException {
        Estoque estoque = findByEstoqueId(request.getEstoqueId());
        criarHistoricoMovimentacao(request.getTipoSaida().name(), estoque, validarQuantidadeSaida(estoque.getQuantidadeDisponivel(), request.getQuantidade()));
    }

    private void criarHistoricoMovimentacao(String tipoMovimentacao, Estoque estoque, Long quantidadeDisponivel) {
        Historico historico = buildHistorico(estoque, quantidadeDisponivel, tipoMovimentacao);
        estoque.atualizarQuantidade(quantidadeDisponivel);
        historicoRepository.save(historico);
        estoqueRepository.save(estoque);
    }

    private Historico buildHistorico(Estoque estoque, Long quantidade, String tipoMovimentacao) {
        Historico historico = Historico.builder()
                .estoque(estoque)
                .antigaQuantidade(estoque.getQuantidadeDisponivel())
                .novaQuantidade(quantidade)
                .tipoMovimentacao(tipoMovimentacao)
                .build();

        estoque.addHistorico(historico);
        return historico;
    }

    private Estoque findByEstoqueId(Long estoqueId) throws ValidationException {
        return estoqueRepository.findById(estoqueId).orElseThrow(() -> new ValidationException("Estoque não encontrado!"));
    }

    private Long validarQuantidadeSaida(Long quantidadeDisponivel, Long quantidade) throws ValidationException {
        if(quantidade > quantidadeDisponivel){
            throw new ValidationException("A quantidade de saida nao pode ser maior do que a disponivel!");
        }
        return quantidadeDisponivel - quantidade;
    }

    private EstoqueResponse buildEstoqueResponse(Estoque estoque) {
        return new EstoqueResponse(estoque.getId(), estoque.getCodigoProduto(), estoque.getQuantidadeDisponivel(), estoque.getCodigoBarras());
    }

}
