package com.rstasiowski.bank.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "accepted")
@Getter
@Setter
public class AcceptedCurrenciesConfig {
    public static String DEFAULT_CURRENCY = "PLN";
    private List<String> currencies;
}
