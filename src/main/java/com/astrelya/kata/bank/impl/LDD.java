package com.astrelya.kata.bank.impl;

import java.math.BigDecimal;

/** LDD: annual rate of 1 %, costs money to the bank. */
public class LDD extends AbstractProduct {

    private static final BigDecimal RATE = new BigDecimal("1");

    public LDD(Double amount) {
        super(amount);
    }

    @Override
    public BigDecimal getRate() {
        return RATE;
    }
}
