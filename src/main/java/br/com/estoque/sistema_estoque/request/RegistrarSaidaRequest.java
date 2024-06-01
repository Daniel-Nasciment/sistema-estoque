package br.com.estoque.sistema_estoque.request;

import br.com.estoque.sistema_estoque.enums.ETipoSaida;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegistrarSaidaRequest extends RegistrarMovimentacaoRequest {
    @NotNull
    private ETipoSaida tipoSaida;
}
