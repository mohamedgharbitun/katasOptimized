package com.astrelya.kata.bank.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.astrelya.kata.bank.IBank;
import com.astrelya.kata.bank.IClient;
import com.astrelya.kata.bank.impl.Client;
import com.astrelya.kata.bank.impl.KataBank;

public class TestThirdStep {
	
	IBank bank;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setUp() {
		bank = new KataBank();
		bank.addClient(new Client("client1@test.com"));
		bank.addClient(new Client("client2@test.com"));
		bank.addClient(new Client("client3@test.com"));
	}

	@Test
	public void if_we_lend_15000_we_get_minus_25() {
		Optional<IClient> clientOpt = bank.searchClient("client1@test.com");
		
		if(clientOpt.isEmpty()) {
			fail("client1@test.com should exist");
		}
		
		IClient client = clientOpt.get();
		client.addProduct("Pret",15000.0);
		assertEquals(0,BigDecimal.valueOf(-25.0).compareTo(client.getMonthlyBalance()));
		
	}
	
	@Test
	public void monthly_should_be() {
		Optional<IClient> clientOpt = bank.searchClient("client2@test.com");
		
		if(clientOpt.isEmpty()) {
			fail("client2@test.com should exist");
		}
		
		IClient client2 = clientOpt.get();
		clientOpt = bank.searchClient("client1@test.com");
		
		if(clientOpt.isEmpty()) {
			fail("client1@test.com should exist");
		}
		
		IClient client1 = clientOpt.get();
		clientOpt = bank.searchClient("client3@test.com");
		
		if(clientOpt.isEmpty()) {
			fail("client3@test.com should exist");
		}
		
		IClient client3 = clientOpt.get();
		
		client1.addProduct("LivretA",1000.0);
		client1.addProduct("Pret",42000.0);
		
		assertEquals(0,BigDecimal.valueOf(-69.375).compareTo(client1.getMonthlyBalance()));
		
		client2.addProduct("LivretA",1000.0);
		client2.addProduct("LDD",2100.0);
		client2.addProduct("CompteAVue",6000.0);
		
		assertEquals(0,BigDecimal.valueOf(4.875).compareTo(client2.getMonthlyBalance()));
		
		client3.addProduct("LivretA",2400.0);
		client3.addProduct("LDD",9000.0);
		client3.addProduct("CompteAVue",3000.0);
		client3.addProduct("Pret",12000.0);
		
		assertEquals(0,BigDecimal.valueOf(-9.75).compareTo(client3.getMonthlyBalance()));
		
		assertEquals(0,BigDecimal.valueOf(74.25).compareTo(bank.getMonthlyPNL()));
		
	}
	
}
