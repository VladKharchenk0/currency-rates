package com.exchange.exchangerateservice.domain;

import java.time.LocalDate;

public class DateNotFoundException extends RuntimeException {
    public DateNotFoundException(LocalDate date) {
        super("No exchange rate with a date of " + date + " was found.");
    }
}
