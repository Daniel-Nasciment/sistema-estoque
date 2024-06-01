package br.com.estoque.sistema_estoque.controller;

import br.com.estoque.sistema_estoque.exception.ValidationException;
import br.com.estoque.sistema_estoque.request.EstoqueRequest;
import br.com.estoque.sistema_estoque.request.EntradaEstoqueRequest;
import br.com.estoque.sistema_estoque.request.SaidaEstoqueRequest;
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
    public ResponseEntity<?> registrarEstoque(@RequestBody @Valid EstoqueRequest request){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(estoqueService.registrarEstoque(request));
    }


    @PutMapping(value = "/registrarEntrada")
    public ResponseEntity<?> registrarEntradaEstoque(@RequestBody @Valid EntradaEstoqueRequest request) throws ValidationException {
        estoqueService.registrarEntradaEstoque(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/registrarSaida")
    public ResponseEntity<?> registrarSaidaEstoque(@RequestBody @Valid SaidaEstoqueRequest request) throws ValidationException {
        estoqueService.registrarSaidaEstoque(request);
        return ResponseEntity.ok().build();
    }



}
