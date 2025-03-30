package com.rstasiowski.bank.impl;

import com.rstasiowski.bank.interfaces.TransferValidationRule;
import com.rstasiowski.bank.model.BankAccount;
import com.rstasiowski.bank.model.Money;
import org.springframework.stereotype.Component;

@Component
public class EnoughFundsRule implements TransferValidationRule {

    @Override
    public void validate(BankAccount sender, Money amount) {
        if (sender.getBalance().compareTo(amount) < 0)
            throw new IllegalArgumentException("Not enough funds to transfer");
    }
}
