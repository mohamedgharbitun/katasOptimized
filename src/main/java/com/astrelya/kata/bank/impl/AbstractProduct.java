package com.astrelya.kata.bank.impl;

import com.astrelya.kata.bank.IProduct;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Base class of every product: the monthly value formula lives here, once.
 * <p>
 * Formula from the statement: {@code amount * (rate / 100) / 12}.
 * Sign convention: a positive value is a cost for the bank, a negative value is a revenue.
 */
public abstract class AbstractProduct implements IProduct {

    private static final BigDecimal PERCENT_DIVISOR = BigDecimal.valueOf(100);
    private static final BigDecimal MONTHS_PER_YEAR = BigDecimal.valueOf(12);
    private static final int SCALE = 10;

    private final BigDecimal amount;

    protected AbstractProduct(Double amount) {
        if (amount == null || !Double.isFinite(amount) || amount < 0) {
            throw new IllegalArgumentException(
                    "amount must be a positive number but was " + amount);
        }
        this.amount = BigDecimal.valueOf(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @return the annual rate, in percent (0.75 means 0.75 %)
     */
    public abstract BigDecimal getRate();

    /**
     * Unsigned monthly amount: {@code amount * (rate / 100) / 12}.
     * A single division is used to avoid intermediate rounding.
     */
    protected BigDecimal computeMonthlyAmount() {
        return amount.multiply(getRate())
                .divide(PERCENT_DIVISOR.multiply(MONTHS_PER_YEAR), SCALE, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getMonthlyValue() {
        return computeMonthlyAmount();
    }
}
