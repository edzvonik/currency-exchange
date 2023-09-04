package com.dzvonik.repository;

import com.dzvonik.model.ExchangeRate;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcExchangeRatesRepositoryImpl implements CrudRepository<ExchangeRate> {

    @Override
    public List<ExchangeRate> findAll() throws SQLException {



        return null;
    }

    @Override
    public Optional<ExchangeRate> findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public ExchangeRate save(ExchangeRate entity) throws SQLException {
        return null;
    }

    @Override
    public void update(ExchangeRate entity) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }

}
