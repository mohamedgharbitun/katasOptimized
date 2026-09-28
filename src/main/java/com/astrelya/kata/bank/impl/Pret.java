package com.astrelya.kata.bank.impl;

import java.math.BigDecimal;

/** Pret: money lent by the bank at 2 %, it is a revenue so its monthly value is negative. */
public class Pret extends AbstractProduct {

    private static final BigDecimal RATE = new BigDecimal("2");

    public Pret(Double amount) {
        super(amount);
    }

    @Override
    public BigDecimal getRate() {
        return RATE;
    }

    @Override
    public BigDecimal getMonthlyValue() {
        return computeMonthlyAmount().negate();
    }
}
