package br.com.estoque.sistema_estoque.response;

public record HistoricoResponse(Long estoqueId, String codigoProduto, Long codigoBarras, String tipoMovimentacao,
                                Long novaQuantidade, Long antigaQuantidade, java.time.LocalDateTime criadoEm) {
}
