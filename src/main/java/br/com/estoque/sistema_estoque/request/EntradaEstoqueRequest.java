package br.com.estoque.sistema_estoque.request;

import br.com.estoque.sistema_estoque.enums.ETipoEntrada;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EntradaEstoqueRequest {

    @NotNull
    private Long estoqueId;
    @NotNull
    private Long quantidade;
    @NotNull
    private ETipoEntrada tipoEntrada;

}
