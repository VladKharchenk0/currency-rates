package com.exchange.exchangerateservice.service;

import com.exchange.exchangerateservice.config.DataConfig;
import com.exchange.exchangerateservice.domain.CurrencyRate;
import com.exchange.exchangerateservice.domain.DateNotFoundException;
import com.exchange.exchangerateservice.persistence.CurrencyRatesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class CurrencyRateService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Environment environment;
    private final CurrencyRatesRepository repository;
    private final DataConfig dataConfig;


    public CurrencyRateService(Environment environment, CurrencyRatesRepository repository, DataConfig dataConfig) {
        this.environment = environment;
        this.repository = repository;
        this.dataConfig = dataConfig;
    }

    public Iterable<CurrencyRate> saveCurrentCurrencyRates() throws JsonProcessingException {
        String dataFromNbu = getNbuCurrencyRates();
        List<CurrencyRate> currencyRates = List.of(mapper.readValue(dataFromNbu, CurrencyRate[].class));

        repository.saveAll(currencyRates);

        return currencyRates;
    }

    public LocalDate getLastExchangeDate() {
        return repository.lastExchangeDate();
    }

    public Iterable<CurrencyRate> getRatesBy(LocalDate date) {
        List<CurrencyRate> rateList = repository.findByExchangeDate(date);
        if (CollectionUtils.isEmpty(rateList)) {
            throw new DateNotFoundException(date);
        }
        return rateList;
    }

    public void deleteRatesBy(LocalDate date) {
        repository.deleteByExchangeDate(date);
    }

    private String getNbuCurrencyRates() {
        return Arrays.asList(environment.getActiveProfiles()).contains("mock") ?
                dataConfig.getMockData() : restTemplate.getForObject(dataConfig.getNbuCurrencyUri(), String.class);
    }

}
