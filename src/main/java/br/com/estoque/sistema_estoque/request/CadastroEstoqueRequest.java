package br.com.estoque.sistema_estoque.request;

import br.com.estoque.sistema_estoque.model.Estoque;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Schema
@Getter
public class CadastroEstoqueRequest {

    @NotBlank
    private String codigoProduto;
    @NotNull
    @Positive
    private Long quantidadeDisponivel;
    @NotNull
    private Long codigoBarras;

    public Estoque toModel() {
        return new Estoque(this.codigoProduto, this.quantidadeDisponivel, this.codigoBarras);
    }
}
