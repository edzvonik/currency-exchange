package com.dzvonik.repository;

import java.sql.SQLException;
import java.util.Optional;

public interface ExchangeRateRepository<ExchangeRate> extends CrudRepository<ExchangeRate> {

    Optional<ExchangeRate> findByPair(String baseCurrencyCode, String targetCurrencyCode) throws SQLException;

}
