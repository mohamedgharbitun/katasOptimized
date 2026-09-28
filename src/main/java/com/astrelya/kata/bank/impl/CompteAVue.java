package com.astrelya.kata.bank.impl;

import java.math.BigDecimal;

/** CompteAVue: annual rate of 0.5 %, costs money to the bank. */
public class CompteAVue extends AbstractProduct {

    private static final BigDecimal RATE = new BigDecimal("0.5");

    public CompteAVue(Double amount) {
        super(amount);
    }

    @Override
    public BigDecimal getRate() {
        return RATE;
    }
}
