package com.exchange.exchangerateservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "exchange")
@Configuration
public class DataConfig {

    private String nbuCurrencyUri;

    public String getMockData() {
        return """
                [
                  {
                  "r030": 36,
                  "txt": "Австралійський долар",
                  "rate": 25.8334,
                  "cc": "AUD",
                  "exchangedate": "11.04.2024"
                  },
                  {
                  "r030": 124,
                  "txt": "Канадський долар",
                  "rate": 28.7697,
                  "cc": "CAD",
                  "exchangedate": "11.04.2024"
                  }
                ]
                """;
    }

    public String getNbuCurrencyUri() {
        return nbuCurrencyUri;
    }

    public void setNbuCurrencyUri(String nbuCurrencyUri) {
        this.nbuCurrencyUri = nbuCurrencyUri;
    }
}

