package com.rstasiowski.bank.impl;

import com.rstasiowski.bank.factory.MoneyFactory;
import com.rstasiowski.bank.interfaces.CurrencyConverter;
import com.rstasiowski.bank.model.Money;
import com.rstasiowski.bank.service.CacheService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Currency;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class NbpCurrencyConverter implements CurrencyConverter {

    private MoneyFactory moneyFactory;
    private CacheService cacheService;
    private RestTemplate restTemplate = new RestTemplate();

    private final String NBP_CACHE_KEY_PREFIX = "NBP_EXCHANGE_RATE_";

    @Override
    public Money convert(Money money, Currency toCurrency) {
        BigDecimal exchangeRate = getExchangeRate(money.getCurrency(), toCurrency);
        return money.multiply(exchangeRate);
    }

    private BigDecimal getExchangeRate(Currency fromCurrency, Currency toCurrency) {
        validateExchange(toCurrency);
        if (fromCurrency.equals(toCurrency)) {
            return BigDecimal.ONE;
        }
        String cacheKey = getCacheKey(fromCurrency.getCurrencyCode(), toCurrency.getCurrencyCode());
        String cachedValue = (String) cacheService.loadFromCache(cacheKey);
        if (cachedValue != null) {
            return new BigDecimal(cachedValue);
        }
        BigDecimal exchangeRate = fetchExchangeRate(toCurrency);
        cacheService.saveToCache(cacheKey, exchangeRate, Duration.ofHours(1));
        return exchangeRate;
    }

    private void validateExchange(Currency toCurrency) {
        if (!toCurrency.getCurrencyCode().equalsIgnoreCase("PLN")) {
            throw new IllegalArgumentException("This converter allows only exchange to PLN");
        }
    }

    private String getCacheKey(String fromCurrency, String toCurrency) {
        return NBP_CACHE_KEY_PREFIX + fromCurrency + "_" + toCurrency;
    }

    private BigDecimal fetchExchangeRate(Currency currency) {
        String url = "https://api.nbp.pl/api/exchangerates/rates/{currencyCode}/?format=json";
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class, currency.getCurrencyCode());
        List<Map<String, Object>> exchangeRates = (List<Map<String, Object>>) response.getBody().get("rates");
        return new BigDecimal(exchangeRates.get(0).get("mid").toString());
    }

}
