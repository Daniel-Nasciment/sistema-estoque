package br.com.estoque.sistema_estoque.controller;

import br.com.estoque.sistema_estoque.exception.ValidationException;
import br.com.estoque.sistema_estoque.service.HistoricoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/historico")
@RequiredArgsConstructor
public class HistoricoController {


    private final HistoricoService historicoService;

    @GetMapping(value = "/{estoqueId}")
    public Page<?> listHistorico(Pageable pageable, @PathVariable Long estoqueId) throws ValidationException {
        return historicoService.findAllHistorico(pageable, estoqueId);
    }

}
