package com.dzvonik.repository;

import com.dzvonik.DataSourceConfig;
import com.dzvonik.model.Currency;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCurrencyRepositoryImpl implements CurrencyRepository<Currency> {

    private final DataSource dataSource = DataSourceConfig.getINSTANCE();

    @Override
    public Optional findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional findByCode(String code) throws SQLException {
        final String query = "SELECT * FROM currencies WHERE code = ?;";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            Currency currency = new Currency (
                rs.getLong("id"),
                rs.getString("code"),
                rs.getString("full_name"),
                rs.getString("sign")
            );

            return Optional.of(currency);
        }
    }

    @Override
    public List findAll() throws SQLException {
        final String query = "SELECT * FROM currencies;";
        List<Currency> currencies = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Currency currency = new Currency(
                        rs.getLong("id"),
                        rs.getString("code"),
                        rs.getString("full_name"),
                        rs.getString("sign")
                );
                currencies.add(currency);
            }
        }

        return currencies;
    }

    @Override
    public Currency save(Currency currency) throws SQLException {
        String query = "INSERT INTO currencies (id, code, full_name, sign) VALUES (NEXTVAL('currencies_sequence'), ?, ?, ?) RETURNING id;";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, currency.getCode());
            ps.setString(2, currency.getFullName());
            ps.setString(3, currency.getSign());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                currency.setId(rs.getLong("id"));
            }

        }

        return currency;
    }

    @Override
    public void update(Currency entity) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }

}
