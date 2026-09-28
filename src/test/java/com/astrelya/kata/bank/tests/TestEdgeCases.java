package com.astrelya.kata.bank.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.astrelya.kata.bank.IBank;
import com.astrelya.kata.bank.IClient;
import com.astrelya.kata.bank.impl.Client;
import com.astrelya.kata.bank.impl.KataBank;

public class TestEdgeCases {

    IBank bank;

    @Before
    public void setUp() {
        bank = new KataBank();
        bank.addClient(new Client("client1@test.com"));
    }

    @Test
    public void unknown_product_type_is_rejected() {
        IClient client = bank.searchClient("client1@test.com").get();

        Exception e = assertThrows(IllegalArgumentException.class, () -> client.addProduct("Livret", 100.0));

        assertTrue(e.getMessage().contains("Unknown product type: Livret"));
        assertTrue(client.getProductList().isEmpty());
    }

    @Test
    public void invalid_amount_does_not_register_the_product() {
        IClient client = bank.searchClient("client1@test.com").get();

        assertThrows(IllegalArgumentException.class, () -> client.addProduct("LDD", -10.0));
        assertThrows(IllegalArgumentException.class, () -> client.addProduct("LDD", null));

        assertTrue(client.getProductList().isEmpty());
        client.addProduct("LDD", 2100.0);
        assertEquals(1, client.getProductList().size());
    }

    @Test
    public void product_list_is_read_only() {
        IClient client = bank.searchClient("client1@test.com").get();
        client.addProduct("LivretA", 1000.0);
        client.addProduct("Pret", 15000.0);

        assertEquals(2, client.getProductList().size());
        assertThrows(UnsupportedOperationException.class, () -> client.getProductList().clear());
    }

    @Test
    public void client_list_is_read_only() {
        assertThrows(UnsupportedOperationException.class, () -> bank.getClientList().clear());
        assertEquals(1, bank.getClientList().size());
    }

    @Test
    public void email_is_case_insensitive_for_unicity_and_search() {
        assertThrows(IllegalArgumentException.class, () -> bank.addClient(new Client("CLIENT1@test.com")));
        assertTrue(bank.searchClient("Client1@Test.com").isPresent());
    }

    @Test
    public void search_of_unknown_or_null_email_is_empty() {
        assertFalse(bank.searchClient("nobody@test.com").isPresent());
        assertFalse(bank.searchClient(null).isPresent());
    }

    @Test
    public void null_email_is_rejected() {
        assertThrows(IllegalArgumentException.class, () -> new Client(null));
    }

    @Test
    public void null_client_is_rejected() {
        assertThrows(NullPointerException.class, () -> bank.addClient(null));
    }

    @Test
    public void balance_without_product_and_pnl_of_empty_bank_are_zero() {
        assertEquals(0, BigDecimal.ZERO.compareTo(bank.searchClient("client1@test.com").get().getMonthlyBalance()));
        assertEquals(0, BigDecimal.ZERO.compareTo(new KataBank().getMonthlyPNL()));
    }
}
