package com.exchange.exchangerateservice.web;

import com.exchange.exchangerateservice.domain.CurrencyRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class RatesJsonTest {

    @Autowired
    private JacksonTester<CurrencyRate> json;

    @Test
    void testSerialize() throws Exception {
        var rate = getCurrencyRate();
        var jsonContent = json.write(rate);
        assertThat(jsonContent).extractingJsonPathNumberValue("@.r030")
                .isEqualTo(rate.code());
        assertThat(jsonContent).extractingJsonPathStringValue("@.txt")
                .isEqualTo(rate.name());
        assertThat(jsonContent).extractingJsonPathStringValue("@.cc")
                .isEqualTo(rate.abbreviation());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.rate")
                .isEqualTo(rate.rate());
        assertThat(jsonContent).extractingJsonPathStringValue("@.exchangedate")
                .isEqualTo(String.valueOf(rate.exchangeDate()));
    }

    @Test
    void testDeserialize() throws Exception {
        var content = """
                {
                 "r030": 36,
                 "txt": "Австралійський долар",
                 "rate": 25.8334,
                 "cc": "AUD",
                 "exchangedate": "01.01.2024"
                 }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(getCurrencyRate());
    }

    private static CurrencyRate getCurrencyRate() {
        return CurrencyRate.of(36, "Австралійський долар", "AUD", 25.8334,
                LocalDate.of(2024, 1, 1));
    }
}
