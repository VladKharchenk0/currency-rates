package com.exchange.exchangerateservice.demo;

import com.exchange.exchangerateservice.domain.CurrencyRate;
import com.exchange.exchangerateservice.persistence.CurrencyRatesRepository;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Profile("mock")
public class RatesDataLoader {

    private final CurrencyRatesRepository repository;

    public RatesDataLoader(CurrencyRatesRepository repository) {
        this.repository = repository;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void loadRatesTestData() {
        repository.deleteAll();
        var rate1 = CurrencyRate.of(36, "Австралійський долар", "AUD", 25.8334, LocalDate.of(2024, 1, 1));
        var rate2 = CurrencyRate.of(124, "Канадський долар", "CAD", 28.7697, LocalDate.of(2024, 1, 1));
        repository.saveAll(List.of(rate1, rate2));
    }

}
