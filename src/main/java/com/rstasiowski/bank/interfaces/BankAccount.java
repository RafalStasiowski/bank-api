package com.rstasiowski.bank.interfaces;

import com.rstasiowski.bank.enums.BankAccountType;
import com.rstasiowski.bank.model.Money;
import com.rstasiowski.bank.model.User;

public interface BankAccount {
    Long getId();
    Money getBalance();
    String getAccountNumber();
    BankAccountType getType();
    User getOwner();
    void deposit(Money amount);
    void withdraw(Money amount);
    void initialize(Money balance, User owner, BankAccountType type);
}
