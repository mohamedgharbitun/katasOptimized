package com.astrelya.kata.bank;

import java.math.BigDecimal;
import java.util.Collection;

public interface IClient {

    String getEmail();

    Collection<Object> getProductList();

    BigDecimal getMonthlyBalance();

    void addProduct(String productType, Double amount);
}
