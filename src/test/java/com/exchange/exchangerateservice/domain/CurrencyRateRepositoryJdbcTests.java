package com.exchange.exchangerateservice.domain;

import com.exchange.exchangerateservice.config.DataConfig;
import com.exchange.exchangerateservice.persistence.CurrencyRatesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
public class CurrencyRateRepositoryJdbcTests {

    @Autowired
    private CurrencyRatesRepository repository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    private static final LocalDate DATE = LocalDate.of(2024, 1, 1);

    @Test
    void findRatesByDate() {
        var currencyRate1 = CurrencyRate.of(36, "Австралійський долар", "AUD", 25.8334, DATE);
        var currencyRate2 = CurrencyRate.of(124, "Канадський долар", "CAD", 28.7697, DATE);
        repository.saveAll(List.of(currencyRate1, currencyRate2));

        Iterable<CurrencyRate> actualCurrencyRates = repository.findByExchangeDate(DATE);

        assertThat(StreamSupport.stream(actualCurrencyRates.spliterator(), true)
                .filter(rate -> rate.code().equals(currencyRate1.code()) || rate.code().equals(currencyRate2.code()))
                .collect(Collectors.toList())).hasSize(2);
    }
}
