package com.astrelya.kata.bank.tests;

import com.astrelya.kata.bank.IBank;
import com.astrelya.kata.bank.IClient;
import com.astrelya.kata.bank.impl.Client;
import com.astrelya.kata.bank.impl.KataBank;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestSecondStep {
	
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
	public void if_1000_on_livretA_cost_0_625() {
		Optional<IClient> clientOpt = bank.searchClient("client1@test.com");
		
		if(clientOpt.isEmpty()) {
			fail("client1@test.com should exist");
		}
		
		IClient client = clientOpt.get();
		client.addProduct("LivretA",1000.0);
		assertEquals(0, BigDecimal.valueOf(0.625).compareTo(client.getMonthlyBalance()));
		
	}
	
	@Test
	public void if_2100_on_LDD_cost_1_75() {
		Optional<IClient> clientOpt = bank.searchClient("client2@test.com");
		
		if(clientOpt.isEmpty()) {
			fail("client2@test.com should exist");
		}
		
		IClient client = clientOpt.get();
		client.addProduct("LDD",2100.0);

		assertEquals(0,BigDecimal.valueOf(1.75).compareTo(client.getMonthlyBalance()));
		
	}
	
	@Test
	public void if_3000_on_CompteAVue_cost_1_25() {
		Optional<IClient> clientOpt = bank.searchClient("client3@test.com");
		
		if(clientOpt.isEmpty()) {
			fail("client3@test.com should exist");
		}
		
		IClient client = clientOpt.get();
		client.addProduct("CompteAVue",3000.0);


		assertEquals(0,BigDecimal.valueOf(1.25).compareTo(client.getMonthlyBalance()));
		
	}
	
	@Test
	public void client_cannot_have_2_LDD() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("client1@test.com cannot have two LDD");
		Optional<IClient> clientOpt = bank.searchClient("client1@test.com");
		
		if(clientOpt.isEmpty()) {
			fail("client1@test.com should exist");
		}
		
		IClient client = clientOpt.get();
		client.addProduct("LDD",3000.0);
		client.addProduct("LDD",5000.0);
		
	}
	
	@Test
	public void client_cannot_have_2_LivretA() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("client1@test.com cannot have two LivretA");
		Optional<IClient> clientOpt = bank.searchClient("client1@test.com");
		
		if(clientOpt.isEmpty()) {
			fail("client1@test.com should exist");
		}
		
		IClient client = clientOpt.get();
		client.addProduct("LivretA",3000.0);
		client.addProduct("LivretA",6000.0);
		
	}
	
	@Test
	public void client_cannot_have_2_CAV() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("client1@test.com cannot have two CompteAVue");
		Optional<IClient> clientOpt = bank.searchClient("client1@test.com");
		
		if(clientOpt.isEmpty()) {
			fail("client1@test.com should exist");
		}
		
		IClient client = clientOpt.get();
		client.addProduct("CompteAVue",3000.0);
		client.addProduct("CompteAVue",1000.0);
		
	}
}
