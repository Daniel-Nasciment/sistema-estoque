package br.com.estoque.sistema_estoque.request;

import br.com.estoque.sistema_estoque.enums.ETipoSaida;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SaidaEstoqueRequest {
    @NotNull
    private Long estoqueId;
    @NotNull
    private Long quantidade;
    @NotNull
    private ETipoSaida tipoSaida;
}
