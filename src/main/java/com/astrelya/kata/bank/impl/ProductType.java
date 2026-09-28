package com.astrelya.kata.bank.impl;

import com.astrelya.kata.bank.IProduct;

import java.util.function.Function;

/**
 * Catalogue of the products sold by the bank.
 * Adding a product means adding a constant here and a class, nothing else to touch.
 */
public enum ProductType {

    LIVRET_A("LivretA", LivretA::new),
    LIVRET_DD("LDD", LDD::new),
    COMPTE_A_VUE("CompteAVue", CompteAVue::new),
    PRET("Pret", Pret::new);

    private final String label;
    private final Function<Double, IProduct> factory;

    ProductType(String label, Function<Double, IProduct> factory) {
        this.label = label;
        this.factory = factory;
    }

    public String getLabel() {
        return label;
    }

    public IProduct create(Double amount) {
        return factory.apply(amount);
    }

    public static ProductType fromLabel(String label) {
        for (ProductType type : values()) {
            if (type.label.equals(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown product type: " + label);
    }
}
