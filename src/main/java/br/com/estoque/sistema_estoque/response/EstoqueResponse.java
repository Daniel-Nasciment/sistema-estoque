package br.com.estoque.sistema_estoque.response;

public record EstoqueResponse(Long id, String codigoProduto, Long quantidadeDisponivel, Long codigoBarras) {
}
