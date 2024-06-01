package br.com.estoque.sistema_estoque.controller;

import br.com.estoque.sistema_estoque.exception.ValidationException;
import br.com.estoque.sistema_estoque.request.CadastroEstoqueRequest;
import br.com.estoque.sistema_estoque.request.RegistrarEntradaRequest;
import br.com.estoque.sistema_estoque.request.RegistrarSaidaRequest;
import br.com.estoque.sistema_estoque.service.EstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/estoque")
@RequiredArgsConstructor
public class EstoqueController {


    private final EstoqueService estoqueService;

    @PostMapping
    public ResponseEntity<?> cadastrarEstoque(@RequestBody @Valid CadastroEstoqueRequest request){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(estoqueService.cadastrarEstoque(request));
    }


    @PutMapping(value = "/registrarEntrada")
    public ResponseEntity<?> registrarEntrada(@RequestBody @Valid RegistrarEntradaRequest request) throws ValidationException {
        estoqueService.registrarEntrada(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/registrarSaida")
    public ResponseEntity<?> registrarSaidaEstoque(@RequestBody @Valid RegistrarSaidaRequest request) throws ValidationException {
        estoqueService.registrarSaida(request);
        return ResponseEntity.ok().build();
    }



}
