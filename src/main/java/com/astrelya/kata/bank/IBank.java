package com.astrelya.kata.bank;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public interface IBank {

    Collection<IClient> getClientList();

    void addClient(IClient client);

    Optional<IClient> searchClient(String email);

    BigDecimal getMonthlyPNL();
}
