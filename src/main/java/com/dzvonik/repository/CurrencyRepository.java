package com.dzvonik.repository;

import java.sql.SQLException;
import java.util.Optional;

public interface CurrencyRepository<Currency> extends CrudRepository<Currency> {

    Optional<Currency> findByCode(String code) throws SQLException;

}
