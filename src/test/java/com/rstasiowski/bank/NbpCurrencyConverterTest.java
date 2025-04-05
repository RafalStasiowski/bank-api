package com.rstasiowski.bank;

import com.rstasiowski.bank.factory.MoneyFactory;
import com.rstasiowski.bank.impl.NbpCurrencyConverter;
import com.rstasiowski.bank.model.Money;
import com.rstasiowski.bank.service.CacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@SpringBootTest
@ActiveProfiles("test")
public class NbpCurrencyConverterTest {

    private NbpCurrencyConverter nbpCurrencyConverter;
    @Autowired
    private MoneyFactory moneyFactory;
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    @Mock
    private CacheService cacheService;

    @BeforeEach
    public void setUp() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        nbpCurrencyConverter = new NbpCurrencyConverter(moneyFactory, cacheService, restTemplate);
    }

    @Test
    public void testGetExchangeRate() {
        String jsonResponse = """
            {
              "table": "A",
              "currency": "dolar amerykański",
              "code": "USD",
              "rates": [
                {
                  "no": "123/A/NBP/2023",
                  "effectiveDate": "2023-03-24",
                  "mid": 4.20
                }
              ]
            }
            """;

        String url = "https://api.nbp.pl/api/exchangerates/rates/A/PLN/?format=json";
        mockServer.expect(requestTo(url))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        Money usdMoney = moneyFactory.create(BigDecimal.valueOf(100), "USD");
        Money rate = nbpCurrencyConverter.convert(usdMoney, Currency.getInstance("PLN"));
        Money resultMoney = moneyFactory.create(BigDecimal.valueOf(420.0), "PLN");
        assert resultMoney.equals(rate);

        mockServer.verify();
    }
}
