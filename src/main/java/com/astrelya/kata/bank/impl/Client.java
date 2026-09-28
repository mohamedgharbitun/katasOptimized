package com.astrelya.kata.bank.impl;

import com.astrelya.kata.bank.IClient;
import com.astrelya.kata.bank.IProduct;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Client implements IClient {

    /* A standard example of a valid email */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final String email;
    private final Map<ProductType, IProduct> products = new EnumMap<>(ProductType.class);

    public Client(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException(email + " is not a valid email");
        }
        this.email = email;
    }

    @Override
    public String getEmail() {
        return email;
    }

    /**
     * @return a read-only view of the products owned by this client
     */
    @Override
    public Collection<Object> getProductList() {
        return Collections.<Object>unmodifiableCollection(products.values());
    }

    /**
     * @return the sum of the monthly value of each product (cost > 0, revenue < 0)
     */
    @Override
    public BigDecimal getMonthlyBalance() {
        return products.values().stream()
                .map(IProduct::getMonthlyValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * @param productType label of the product (LivretA, LDD, CompteAVue, Pret)
     * @param amount      amount put on (or lent through) the product
     * @throws IllegalArgumentException if the product is unknown, already owned, or the amount invalid
     */
    @Override
    public void addProduct(String productType, Double amount) {
        ProductType type = ProductType.fromLabel(productType);

        if (products.containsKey(type)) {
            throw new IllegalArgumentException(email + " cannot have two " + type.getLabel());
        }

        products.put(type, type.create(amount));
    }
}
