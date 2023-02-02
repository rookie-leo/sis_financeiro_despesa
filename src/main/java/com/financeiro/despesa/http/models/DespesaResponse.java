package com.financeiro.despesa.http.models;

import com.financeiro.despesa.entity.Despesa;

import com.despesa.entity.enums.Categoria;
import java.math.BigDecimal;

public class DespesaResponse {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private String data;
    private Categoria categoria;

    public DespesaResponse(Despesa despesa) {
        this.id = despesa.getId();
        this.descricao = despesa.getDescricao();
        this.valor = despesa.getValor();
        this.data = despesa.getDataEntrada().toString();
        this.categoria = Categoria.toCategoria(despesa.getCategoria());
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getData() {
        return data;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
