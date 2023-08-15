package com.dzvonik;

import org.sqlite.JDBC;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DbHandler {

    private static final String CON_STR = "jdbc:sqlite::memory:";
    private static DbHandler instance = null;
    private Connection connection;

    public static synchronized DbHandler getInstance() throws SQLException, IOException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    private DbHandler() throws SQLException, IOException {
        DriverManager.registerDriver(new JDBC());
        
        this.connection = DriverManager.getConnection(CON_STR);

        try (Statement statement = this.connection.createStatement()) {
            URL queryURL = this.getClass().getResource("/init.sql");
            String query = Files.readString(Paths.get(queryURL.getPath()));
            statement.executeUpdate(query);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Currency> getCurrencies() {
        try (Statement statement = this.connection.createStatement()) {
            List<Currency> currencies = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM currencies;");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");
                String fullName = resultSet.getString("full_name");
                String sign = resultSet.getString("sign");

                Currency currency = new Currency(id, code, fullName, sign);
                currencies.add(currency);
            }
            return currencies;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
