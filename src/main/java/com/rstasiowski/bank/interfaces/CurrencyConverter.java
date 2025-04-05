package com.rstasiowski.bank.interfaces;

import com.rstasiowski.bank.model.Money;

import java.math.BigDecimal;
import java.util.Currency;

public interface CurrencyConverter {
    public Money convert(Money money, Currency toCurrency);
}
