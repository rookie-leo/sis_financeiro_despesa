package com.financeiro.despesa.entity.enums;

public enum Categoria {

    ALIMENTACAO("Alimentacao"),
    SAUDE("Saude"),
    MORADIA("Moradia"),
    TRANSPORTE("Transporte"),
    EDUCACAO("Educacao"),
    LAZER("Lazer"),
    IMPREVISTOS("Imprevistos"),
    OUTROS("Outros");

    private String value;

    Categoria(String value) {
        this.value = value;
    }

    public static Categoria toCategoria(String categoria) {
        if (categoria == null) {
            return Categoria.OUTROS;
        }
        return Categoria.valueOf(categoria.toUpperCase());
    }

}
