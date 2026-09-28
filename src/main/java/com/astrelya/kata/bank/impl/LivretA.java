package com.astrelya.kata.bank.impl;

import java.math.BigDecimal;

/** LivretA: annual rate of 0.75 %, costs money to the bank. */
public class LivretA extends AbstractProduct {

    private static final BigDecimal RATE = new BigDecimal("0.75");

    public LivretA(Double amount) {
        super(amount);
    }

    @Override
    public BigDecimal getRate() {
        return RATE;
    }
}
