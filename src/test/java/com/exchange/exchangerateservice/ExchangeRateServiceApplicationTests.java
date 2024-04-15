package com.exchange.exchangerateservice;

import com.exchange.exchangerateservice.domain.CurrencyRate;
import com.exchange.exchangerateservice.persistence.CurrencyRatesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExchangeRateServiceApplicationTests {

    private static final int ALL_CURRENCIES = 61;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CurrencyRatesRepository repository;

    @Test
    @Order(1)
    void whenFirstGetRequestThenRatesSavedInDB() {
        Iterable<CurrencyRate> currencyRates = repository.findAll();
        Assertions.assertTrue(((Collection<?>) currencyRates).isEmpty());

        callExchangeEndpoint();

        currencyRates = repository.findAll();
        Assertions.assertEquals(ALL_CURRENCIES, ((Collection<?>) currencyRates).size());
    }

    @Test
    @Order(2)
    void whenSecondGetRequestThenRatesInDBStayedUnchangeable() {
        callExchangeEndpoint();

        Iterable<CurrencyRate> currencyRates = repository.findAll();
        Assertions.assertEquals(ALL_CURRENCIES, ((Collection<?>) currencyRates).size());
    }

    @Test
    @Order(3)
    void testDeleteByDateSuccessful() {
        LocalDate date = repository.lastExchangeDate();
        List<CurrencyRate> rates = repository.findByExchangeDate(date);

        Assertions.assertEquals(ALL_CURRENCIES, rates.size());

        webTestClient.delete()
                .uri("/exchange/" + date)
                .exchange()
                .expectStatus().isNoContent();

        rates = (List<CurrencyRate>) repository.findAll();
        Assertions.assertTrue(rates.isEmpty());
    }

    private void callExchangeEndpoint() {
        webTestClient.get()
                .uri("/exchange")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ArrayList.class).value(rate -> {
                    assertThat(rate).isNotNull();
                    assertThat(rate.size()).isEqualTo(ALL_CURRENCIES);
                });
    }
}
