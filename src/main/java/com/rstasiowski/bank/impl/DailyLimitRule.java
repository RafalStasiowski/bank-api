package com.rstasiowski.bank.impl;

import com.rstasiowski.bank.interfaces.BankAccount;
import com.rstasiowski.bank.interfaces.TransferValidationRule;
import com.rstasiowski.bank.model.Money;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Currency;

@Component
@AllArgsConstructor
public class DailyLimitRule implements TransferValidationRule {
    public static final Money MAX_DAILY_LIMIT = Money.of(new BigDecimal("5000.00"), Currency.getInstance("PLN"));

    @Override
    public void validate(BankAccount sender, Money amount) {
        if (amount.getAmount().compareTo(MAX_DAILY_LIMIT.getAmount()) > 0) {
            throw new IllegalArgumentException("Transfer exceeds daily limit");
        }
    }
}