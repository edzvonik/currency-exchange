package com.dzvonik.repository;

import com.dzvonik.DataSourceConfig;
import com.dzvonik.model.Currency;
import com.dzvonik.model.ExchangeRate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcExchangeRateRepositoryImpl implements CrudRepository<ExchangeRate> {

    private final DataSource dataSource = DataSourceConfig.getINSTANCE();

    @Override
    public List<ExchangeRate> findAll() throws SQLException {
        String query = "SELECT er.id AS id,\n" +
                "bc.id AS base_id,\n" +
                "bc.code AS base_code,\n" +
                "bc.full_name AS base_full_name,\n" +
                "bc.sign AS base_sign,\n" +
                "tc.id AS target_id,\n" +
                "tc.code AS target_code,\n" +
                "tc.full_name AS target_full_name,\n" +
                "tc.sign AS target_sign,\n" +
                "er.rate AS rate\n" +
                "FROM exchange_rates er\n" +
                "JOIN currencies bc ON er.base_currency_id = bc.id\n" +
                "JOIN currencies tc ON er.target_currency_id = tc.id\n" +
                "ORDER BY er.id;\n";

        List<ExchangeRate> exchangeRates = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Currency baseCurrency = new Currency (
                        rs.getLong("base_id"),
                        rs.getString("base_code"),
                        rs.getString("base_full_name"),
                        rs.getString("base_sign")
                );

                Currency targetCurrency = new Currency (
                        rs.getLong("target_id"),
                        rs.getString("target_code"),
                        rs.getString("target_full_name"),
                        rs.getString("target_sign")
                );

                ExchangeRate exchangeRate = new ExchangeRate (
                        rs.getLong("id"),
                        baseCurrency,
                        targetCurrency,
                        rs.getDouble("rate")
                );

                exchangeRates.add(exchangeRate);
            }
        }

        return exchangeRates;
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
