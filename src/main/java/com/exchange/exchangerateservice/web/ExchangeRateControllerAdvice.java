package com.exchange.exchangerateservice.web;

import com.exchange.exchangerateservice.domain.DateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExchangeRateControllerAdvice {

    @ExceptionHandler(DateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String dateNotFoundHandler(DateNotFoundException ex) {
        return ex.getMessage();
    }
}
