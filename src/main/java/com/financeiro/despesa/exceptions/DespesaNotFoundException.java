package com.financeiro.despesa.exceptions;

public class DespesaNotFoundException extends RuntimeException {

    public DespesaNotFoundException(String message) {
        super(message);
    }
}
