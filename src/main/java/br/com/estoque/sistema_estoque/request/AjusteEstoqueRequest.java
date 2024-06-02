package br.com.estoque.sistema_estoque.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AjusteEstoqueRequest extends RegistrarMovimentacaoRequest{
    @NotBlank
    private String codigoProduto;
    @NotNull
    private Long codigoBaras;
    @JsonIgnore
    private final String tipoMovimentacao = "AJUSTE";
}
