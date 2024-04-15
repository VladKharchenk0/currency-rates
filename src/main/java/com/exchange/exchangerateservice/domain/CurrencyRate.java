package com.exchange.exchangerateservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record CurrencyRate(
        @Id
        @JsonIgnore
        Long id,
        Integer code,
        String name,
        String abbreviation,
        Double rate,
        LocalDate exchangeDate
) {

    @JsonCreator
    public static CurrencyRate createCurrencyRates(
            @JsonProperty("r030") Integer code,
            @JsonProperty("txt") String name,
            @JsonProperty("cc") String abbreviation,
            @JsonProperty("rate") Double rate,
            @JsonProperty("exchangedate") String exchangeDate) {
        return new CurrencyRate(null, code, name, abbreviation, rate, LocalDate.parse(exchangeDate,
                DateTimeFormatter.ofPattern("dd.MM.yyyy")));
    }

    public static CurrencyRate of(
            Integer code,
            String name,
            String abbreviation,
            Double rate,
            LocalDate exchangeDate
    ) {
        return new CurrencyRate(null, code, name, abbreviation, rate, exchangeDate);
    }
}
