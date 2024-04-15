package com.exchange.exchangerateservice.web;

import com.exchange.exchangerateservice.domain.DateNotFoundException;
import com.exchange.exchangerateservice.service.CurrencyRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeRateController.class)
public class CurrencyRateControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyRateService service;

    @Test
    void whenGetRateDateNotExistingThenShouldReturn404() throws Exception {
        LocalDate date = LocalDate.of(2024, 1, 1);
        given(service.getRatesBy(date))
                .willThrow(DateNotFoundException.class);
        mockMvc
                .perform(get("/exchange/" + date))
                .andExpect(status().isNotFound());
    }
}
