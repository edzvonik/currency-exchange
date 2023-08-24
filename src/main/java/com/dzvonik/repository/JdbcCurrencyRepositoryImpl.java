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

public class JdbcCurrencyRepositoryImpl implements CurrencyRepository {

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

            Currency currency = new Currency(
                rs.getInt("id"),
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
                        rs.getInt("id"),
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
    public Long save(Object entity) throws SQLException {
        String query = "INSERT INTO currencies (code, full_name, sign) VALUES (?, ?, ?);";


        return null;
    }

    @Override
    public void update(Object entity) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }

}
