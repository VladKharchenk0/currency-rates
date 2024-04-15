package com.exchange.exchangerateservice.web;

import com.exchange.exchangerateservice.domain.CurrencyRate;
import com.exchange.exchangerateservice.service.CurrencyRateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/exchange")
public class ExchangeRateController {

    private final CurrencyRateService currencyRateService;

    public ExchangeRateController(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @GetMapping
    public Iterable<CurrencyRate> getRates() throws JsonProcessingException {
        LocalDate lastExchangeDate = currencyRateService.getLastExchangeDate();
        if (lastExchangeDate == null || LocalDate.now().isAfter(lastExchangeDate)) {
            return currencyRateService.saveCurrentCurrencyRates();
        }
        return currencyRateService.getRatesBy(lastExchangeDate);
    }

    @GetMapping("{date}")
    public Iterable<CurrencyRate> getRatesByDate(@PathVariable LocalDate date) {
        return currencyRateService.getRatesBy(date);
    }

    @DeleteMapping("{date}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRates(@PathVariable String date) {
        currencyRateService.deleteRatesBy(LocalDate.parse(date));
    }
}
