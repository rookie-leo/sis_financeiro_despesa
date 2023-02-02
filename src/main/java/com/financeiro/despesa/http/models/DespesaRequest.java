package com.financeiro.despesa.http.models;

import com.financeiro.despesa.entity.Despesa;
import com.despesa.entity.enums.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class DespesaRequest {

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal valor;

    private Categoria categoria;

    public DespesaRequest(String descricao, BigDecimal valor, String categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = Categoria.toCategoria(categoria);
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Despesa toModel() {
        return new Despesa(this.descricao, this.valor, this.categoria);
    }

}
