package com.financeiro.despesa.services;

import com.financeiro.despesa.entity.Despesa;
import com.financeiro.despesa.exceptions.DespesaDuplicadaException;
import com.financeiro.despesa.exceptions.DespesaNotFoundException;
import com.financeiro.despesa.http.models.DespesaRequest;
import com.financeiro.despesa.http.models.DespesaResponse;
import com.financeiro.despesa.repositories.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DespesaService {

    private DespesaRepository repository;

    @Autowired
    public DespesaService(DespesaRepository repository) {
        this.repository = repository;
    }

    public DespesaResponse cadastrar(DespesaRequest request) {
        verificaDuplicidade(request);
        Despesa despesa = request.toModel();

        repository.save(despesa);

        return new DespesaResponse(despesa);
    }

    private void verificaDuplicidade(DespesaRequest request) {
        var isEncontrado = repository.findAll()
                .stream()
                .anyMatch(despesa ->
                        despesa.getDataEntrada().getMonth().equals(LocalDateTime.now().getMonth()) &&
                                despesa.getDescricao().equals(request.getDescricao())
                );

        if (isEncontrado) {
            throw new DespesaDuplicadaException("Despesa já cadastrada!");
        }
    }

    public List<DespesaResponse> listar() {
        List<DespesaResponse> responseList = new ArrayList<>();

        repository.findAll()
                .forEach(despesa -> {
                    responseList.add(new DespesaResponse(despesa));
                });

        return responseList;
    }

    public DespesaResponse buscar(Long id) {
        return new DespesaResponse(repository.findById(id).orElseThrow(() ->
                new DespesaNotFoundException("Id não encontrado!")
        ));
    }

    public List<DespesaResponse> buscarPorDescricao(String descricao) {
        List<DespesaResponse> responseList = new ArrayList<>();

        repository.findByDescricao(descricao)
                .forEach(despesa -> {
                    responseList.add(new DespesaResponse(despesa));
                });

        if (responseList.isEmpty()) {
            throw new DespesaNotFoundException("Descrição não encontrada!");
        }

        return responseList;
    }

    public DespesaResponse atualizar(Long id, DespesaRequest request) {
        verificaDuplicidade(request);
        Despesa despesa = repository.findById(id).orElseThrow(() ->
                new DespesaNotFoundException("Id não encontrado!")
        );

        despesa.setValor(request.getValor());
        despesa.setDescricao(request.getDescricao());
        despesa.setDataEntrada(LocalDateTime.now());

        repository.save(despesa);

        return new DespesaResponse(despesa);
    }

    public void deletar(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new RuntimeException("Id informado não encontrado!");
        }
    }

    public List<DespesaResponse> buscarPorAnoEMes(String ano, String mes) {
        List<DespesaResponse> responseList = new ArrayList<>();
        String dataFmt = String.format("%s-%s", ano, mes);

        repository.findByDataEntrada(dataFmt)
                .forEach(despesa ->
                        responseList.add(new DespesaResponse(despesa))
                        );

        if (responseList.isEmpty()) {
            throw new DespesaNotFoundException("Nenhuma despesa encontrada na data informada!");
        }
        return responseList;
    }
}
