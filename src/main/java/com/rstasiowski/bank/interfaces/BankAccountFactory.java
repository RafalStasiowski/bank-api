package com.rstasiowski.bank.interfaces;

import com.rstasiowski.bank.enums.BankAccountType;
import com.rstasiowski.bank.model.BankAccount;
import com.rstasiowski.bank.model.Money;
import com.rstasiowski.bank.model.User;

public interface BankAccountFactory {
    BankAccount createAccount(BankAccountType type, Money balance, User owner);
}
