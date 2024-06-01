package br.com.estoque.sistema_estoque.request;

import br.com.estoque.sistema_estoque.enums.ETipoEntrada;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegistrarEntradaRequest extends RegistrarMovimentacaoRequest {
    @NotNull
    private ETipoEntrada tipoEntrada;

}
