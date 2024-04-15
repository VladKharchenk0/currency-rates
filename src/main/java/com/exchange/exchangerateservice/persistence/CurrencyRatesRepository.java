package com.exchange.exchangerateservice.persistence;

import com.exchange.exchangerateservice.domain.CurrencyRate;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CurrencyRatesRepository extends CrudRepository<CurrencyRate, Long> {
    @Query("SELECT exchange_date FROM currency_rate cr ORDER BY cr.exchange_date DESC LIMIT 1")
    LocalDate lastExchangeDate();

    List<CurrencyRate> findByExchangeDate(LocalDate date);

    @Modifying
    @Transactional
    @Query("delete from currency_rate where exchange_date = :date")
    void deleteByExchangeDate(LocalDate date);
}
