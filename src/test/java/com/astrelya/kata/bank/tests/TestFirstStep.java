package com.astrelya.kata.bank.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.astrelya.kata.bank.IBank;
import com.astrelya.kata.bank.IClient;
import com.astrelya.kata.bank.impl.Client;
import com.astrelya.kata.bank.impl.KataBank;

public class TestFirstStep {
	
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
	public void client_email_must_be_well_formatted() {

		 Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			 new Client("client1");
		    });
		 
		    String expectedMessage = "client1 is not a valid email";
		    String actualMessage = exception.getMessage();
		 
		    assertTrue(actualMessage.contains(expectedMessage));		
	}
	
	@Test
	public void client_must_be_unique() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Client client1@test.com already exist");
		
		bank.addClient(new Client("client1@test.com"));
		
	}
	
	@Test
	public void bank_has_3_clients() {
		assertNotNull("Client list should not be null",bank.getClientList());
		assertEquals("Bank should have 3 clients",3, bank.getClientList().size());
	}
	
	@Test
	public void client_must_be_found() {
		
		Optional<IClient> client = bank.searchClient("client3@test.com");
		assertTrue("Client client3@test.com should be found", client.isPresent());
		assertEquals("client3@test.com", client.get().getEmail());
	}

}
