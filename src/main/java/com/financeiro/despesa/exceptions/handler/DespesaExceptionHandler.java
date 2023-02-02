package com.financeiro.despesa.exceptions.handler;

import com.financeiro.despesa.exceptions.DespesaDuplicadaException;
import com.financeiro.despesa.exceptions.DespesaNotFoundException;
import com.financeiro.despesa.exceptions.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DespesaExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String GENERIC_ERROR_MESSAGE = "Houve um erro inesperado ao processar a requisição!";

    @ExceptionHandler(DespesaDuplicadaException.class)
    public final ResponseEntity<?> handlerDespesaDuplicadaException(DespesaDuplicadaException ex) {
        logger.error("DespesaDuplicadaException", ex);

        var exceptionResponse = new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }

    @ExceptionHandler(DespesaNotFoundException.class)
    public final ResponseEntity<?> handlerDespesaNotFoundException(DespesaNotFoundException ex) {
        logger.error("DespesaNotFoundException", ex);

        var exceptionResponse = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handlerException(Exception ex) {
        logger.error(GENERIC_ERROR_MESSAGE);

        var exceptionResponse = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), exceptionResponse.getStatus());
    }

}
