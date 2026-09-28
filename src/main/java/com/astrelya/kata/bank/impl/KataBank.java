package com.astrelya.kata.bank.impl;

import com.astrelya.kata.bank.IBank;
import com.astrelya.kata.bank.IClient;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class KataBank implements IBank {

    /* Indexed by normalized email: O(1) search and unicity check, insertion order kept */
    private final Map<String, IClient> clients = new LinkedHashMap<>();

    /**
     * Adds a client to the bank.
     *
     * @param client client
     * @throws IllegalArgumentException if a client with the same email (case insensitive) already exists
     */
    @Override
    public void addClient(IClient client) {
        Objects.requireNonNull(client, "client must not be null");

        if (clients.putIfAbsent(normalize(client.getEmail()), client) != null) {
            throw new IllegalArgumentException("Client " + client.getEmail() + " already exist");
        }
    }

    /**
     * @return a read-only view of the clients
     */
    @Override
    public Collection<IClient> getClientList() {
        return Collections.unmodifiableCollection(clients.values());
    }

    /**
     * Searches a client by email (case insensitive).
     *
     * @return the client, or empty if there is none
     */
    @Override
    public Optional<IClient> searchClient(String email) {
        if (email == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(clients.get(normalize(email)));
    }

    /**
     * @return what the bank gains (> 0) or loses (< 0) each month: the opposite of the clients' balances
     */
    @Override
    public BigDecimal getMonthlyPNL() {
        return clients.values().stream()
                .map(IClient::getMonthlyBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .negate();
    }

    private static String normalize(String email) {
        return email.toLowerCase(Locale.ROOT);
    }
}
