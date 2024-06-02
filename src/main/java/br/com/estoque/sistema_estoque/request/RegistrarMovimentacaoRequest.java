package br.com.estoque.sistema_estoque.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public abstract class RegistrarMovimentacaoRequest {
    @NotNull
    private Long estoqueId;
    @NotNull
    @Positive
    private Long quantidade;
}
