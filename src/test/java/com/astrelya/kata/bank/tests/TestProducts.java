package com.astrelya.kata.bank.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;

import org.junit.Test;

import com.astrelya.kata.bank.IProduct;
import com.astrelya.kata.bank.impl.CompteAVue;
import com.astrelya.kata.bank.impl.LDD;
import com.astrelya.kata.bank.impl.LivretA;
import com.astrelya.kata.bank.impl.Pret;

public class TestProducts {

    private static void assertSame(String expected, BigDecimal actual) {
        assertEquals(0, new BigDecimal(expected).compareTo(actual));
    }

    @Test
    public void rates_match_the_statement() {
        assertSame("0.75", new LivretA(1.0).getRate());
        assertSame("1", new LDD(1.0).getRate());
        assertSame("0.5", new CompteAVue(1.0).getRate());
        assertSame("2", new Pret(1.0).getRate());
    }

    @Test
    public void monthly_value_is_amount_times_rate_over_12() {
        assertSame("0.625", new LivretA(1000.0).getMonthlyValue());
        assertSame("1.75", new LDD(2100.0).getMonthlyValue());
        assertSame("1.25", new CompteAVue(3000.0).getMonthlyValue());
    }

    @Test
    public void pret_is_a_revenue_so_negative() {
        IProduct pret = new Pret(15000.0);
        assertSame("-25", pret.getMonthlyValue());
    }

    @Test
    public void amount_must_not_be_null() {
        assertThrows(IllegalArgumentException.class, () -> new LivretA(null));
    }

    @Test
    public void amount_must_not_be_negative() {
        assertThrows(IllegalArgumentException.class, () -> new LDD(-1.0));
    }

    @Test
    public void amount_must_be_a_finite_number() {
        assertThrows(IllegalArgumentException.class, () -> new Pret(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> new CompteAVue(Double.POSITIVE_INFINITY));
    }
}
