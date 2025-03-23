package com.rstasiowski.bank.interfaces;

import com.rstasiowski.bank.model.Money;

public interface TransferValidationRule {
    void validate(BankAccount sender, Money amount);
}
