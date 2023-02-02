package com.financeiro.despesa.http.controllers;

import com.financeiro.despesa.http.models.DespesaRequest;
import com.financeiro.despesa.http.models.DespesaResponse;
import com.financeiro.despesa.services.DespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<DespesaResponse> cadastrar(@RequestBody @Valid DespesaRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        var despesaResponse = service.cadastrar(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/despesas/{id}")
                .buildAndExpand(despesaResponse.getId())
                .toUri();

        return ResponseEntity.created(uri).body(despesaResponse);
    }

    @GetMapping()
    public List<DespesaResponse> listar(@RequestParam(value = "descricao", required = false) String descricao) {
        if (descricao == null || descricao.isBlank()) {
            return service.listar();
        }

        return service.buscarPorDescricao(descricao);
    }

    @GetMapping("/{id}")
    public DespesaResponse buscarDespesa(@PathVariable Long id) {
        return service.buscar(id);
    }

    @GetMapping("/{ano}/{mes}")
    public List<DespesaResponse> buscarPorMesEAno(@PathVariable String ano, @PathVariable String mes) {
        return service.buscarPorAnoEMes(ano, mes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaResponse> atualizarDespesa(@PathVariable Long id,
                                                            @RequestBody @Valid DespesaRequest request) {
        DespesaResponse response = service.atualizar(id, request);

        return ResponseEntity.accepted().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDespesa(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.ok().build();
    }

}
