package com.rstasiowski.bank.factory;


import com.rstasiowski.bank.config.AcceptedCurrenciesConfig;
import com.rstasiowski.bank.model.Money;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Currency;

@Component
@AllArgsConstructor
public class MoneyFactory {
    private final AcceptedCurrenciesConfig acceptedCurrenciesConfig;

    public Money create(BigDecimal amount, String currencyCode) {
        validateCurrencyCode(currencyCode);
        Currency currency = Currency.getInstance(currencyCode);
        return Money.of(amount, currency);
    }

    public Money createEmpty(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        return Money.of(BigDecimal.ZERO, currency);
    }

    void validateCurrencyCode(String currencyCode) {
        if (!acceptedCurrenciesConfig.getCurrencies().contains(currencyCode)) {
            throw new IllegalArgumentException("This currency is not supported");
        }
    }
}
