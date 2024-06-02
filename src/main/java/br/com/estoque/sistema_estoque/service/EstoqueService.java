package br.com.estoque.sistema_estoque.service;

import br.com.estoque.sistema_estoque.exception.ValidationException;
import br.com.estoque.sistema_estoque.model.Estoque;
import br.com.estoque.sistema_estoque.model.Historico;
import br.com.estoque.sistema_estoque.repository.EstoqueRepository;
import br.com.estoque.sistema_estoque.request.AjusteEstoqueRequest;
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
    private final HistoricoService historicoService;

    public EstoqueResponse cadastrarEstoque(CadastroEstoqueRequest request) {
        log.info("Iniciando cadastro de estoque.");
        Estoque estoque = estoqueRepository.save(request.toModel());
        EstoqueResponse response = buildEstoqueResponse(estoque);
        log.info("Cadastro de estoque concluído com sucesso: {}", response);
        return response;
    }

    public void registrarEntrada(RegistrarEntradaRequest request) throws ValidationException {
        log.info("Registrando entrada de estoque.");
        Estoque estoque = findByEstoqueId(request.getEstoqueId());
        Historico historico = historicoService.criarHistoricoMovimentacao(request.getTipoEntrada().name(), estoque, estoque.getQuantidadeDisponivel() + request.getQuantidade());
        saveEstoque(estoque);
        historicoService.saveHistorico(historico);
        log.info("Entrada de estoque registrada com sucesso para o estoque ID: {}", request.getEstoqueId());
    }

    public void registrarSaida(RegistrarSaidaRequest request) throws ValidationException {
        log.info("Registrando saída de estoque.");
        Estoque estoque = findByEstoqueId(request.getEstoqueId());
        Historico historico = historicoService.criarHistoricoMovimentacao(request.getTipoSaida().name(), estoque, validarQuantidadeSaida(estoque.getQuantidadeDisponivel(), request.getQuantidade()));
        saveEstoque(estoque);
        historicoService.saveHistorico(historico);
        log.info("Saída de estoque registrada com sucesso para o estoque ID: {}", request.getEstoqueId());
    }

    public void ajustar(AjusteEstoqueRequest request) throws ValidationException {
        log.info("Ajustando estoque.");
        Estoque estoque = findByEstoqueId(request.getEstoqueId());
        Historico historico = historicoService.criarHistoricoMovimentacao(request.getTipoMovimentacao(), estoque, request.getQuantidade());
        estoque.ajustar(request);
        saveEstoque(estoque);
        historicoService.saveHistorico(historico);
        log.info("Ajuste de estoque concluído com sucesso para o estoque ID: {}", request.getEstoqueId());
    }

    private void saveEstoque(Estoque estoque) {
        log.debug("Salvando estoque. Estoque: {}", estoque);
        estoqueRepository.save(estoque);
        log.debug("Estoque salvo com sucesso.");
    }

    private Estoque findByEstoqueId(Long estoqueId) throws ValidationException {
        log.debug("Buscando estoque por ID: {}", estoqueId);
        return estoqueRepository.findById(estoqueId)
                .orElseThrow(() -> new ValidationException("Estoque não encontrado!"));
    }

    private Long validarQuantidadeSaida(Long quantidadeDisponivel, Long quantidade) throws ValidationException {
        log.debug("Validando quantidade de saída. Quantidade Disponível: {}, Quantidade Saída: {}", quantidadeDisponivel, quantidade);
        if (quantidade > quantidadeDisponivel) {
            log.warn("A quantidade de saída ({}) é maior do que a disponível ({})", quantidade, quantidadeDisponivel);
            throw new ValidationException("A quantidade de saída não pode ser maior do que a disponível!");
        }
        return quantidadeDisponivel - quantidade;
    }

    private EstoqueResponse buildEstoqueResponse(Estoque estoque) {
        log.debug("Construindo resposta de estoque. Estoque: {}", estoque);
        return new EstoqueResponse(estoque.getId(), estoque.getCodigoProduto(), estoque.getQuantidadeDisponivel(), estoque.getCodigoBarras());
    }
}
