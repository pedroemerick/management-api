package com.ek.management.v1.model;

public enum IdentifierType {

    CPF(11),
    CNPJ(14);

    public final int length;

    IdentifierType(int length) {
        this.length = length;
    }
}
