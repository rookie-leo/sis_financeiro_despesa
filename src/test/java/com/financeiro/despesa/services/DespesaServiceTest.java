package com.financeiro.despesa.services;

import com.financeiro.despesa.exceptions.DespesaNotFoundException;
import com.financeiro.despesa.http.models.DespesaRequest;
import com.financeiro.despesa.http.models.DespesaResponse;
import com.financeiro.despesa.repositories.DespesaRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DespesaServiceTest {

    private DespesaRequest request = requestBuilder();
    private final DespesaRepository repository = mock(DespesaRepository.class);
    private final DespesaService service = new DespesaService(repository);


    @Test
    public void shouldRegisterNewDespesa() {
        service.cadastrar(request);

        verify(repository, times(1)).save(any());
    }

    @Test
    public void shouldBeThrowNullPointerExceptionWhenRegisteringANewNullDespesa() {
        request = null;
        assertThrows(NullPointerException.class, () -> service.cadastrar(request));
    }

    @Test
    public void shouldGetListOfDespesas() {
        List<DespesaResponse> listaDespesa = service.listar();

        verify(repository, times(1)).findAll();
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenIdNotFound() {
        assertThrows(RuntimeException.class, () -> service.buscar(1L));
        verify(repository, times(1)).findById(any());
    }

    @Test
    public void shouldThrowDespesaNotFoundExceptionWhenDescricaoNotFound() {
        assertThrows(DespesaNotFoundException.class, () -> service.buscarPorDescricao("Descrição teste"));
        verify(repository, times(1)).findByDescricao(any());
    }

    private DespesaRequest requestBuilder() {
        return new DespesaRequest("Descrição teste", new BigDecimal(50.0), "outros");
    }

}