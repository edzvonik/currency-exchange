package com.dzvonik;

import com.dzvonik.model.Currency;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class DBConfig {

    private static final PGSimpleDataSource INSTANCE = new PGSimpleDataSource();

    static {
        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        if (url == null || username == null || password == null) {
            try (InputStream input = DBConfig.class.getResourceAsStream("/db.properties")) {
                Properties prop = new Properties();
                prop.load(input);

                url = prop.getProperty("db.url");
                username = prop.getProperty("db.username");
                password = prop.getProperty("db.password");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        INSTANCE.setURL(url);
        INSTANCE.setUser(username);
        INSTANCE.setPassword(password);
    }

    public static PGSimpleDataSource getINSTANCE() {
        return INSTANCE;
    }

    //
//    private static final String CON_STR = "jdbc:postgresql://";
//    private static DbHandler instance = null;
//    private Connection connection;

//    public static synchronized DbHandler getInstance() throws SQLException, IOException {
//        if (instance == null)
//            instance = new DbHandler();
//        return instance;
//    }

//    private DbHandler() throws SQLException, IOException {
//        // DriverManager.registerDriver(new JDBC());
//        String url = System.getenv("DB_URL");
//        String username = System.getenv("DB_USERNAME");
//        String password = System.getenv("DB_PASSWORD");
//
//        if (url == null || username == null || password == null) {
//            try (InputStream input = DbHandler.class.getResourceAsStream("/db.properties")) {
//                Properties prop = new Properties();
//                prop.load(input);
//
//                url = prop.getProperty("db.url");
//                username = prop.getProperty("db.username");
//                password = prop.getProperty("db.password");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        this.connection = DriverManager.getConnection(url, username, password);
//
////        try (Statement statement = this.connection.createStatement()) {
////            URL queryURL = this.getClass().getResource("/init.sql");
////            String query = Files.readString(Paths.get(queryURL.getPath()));
////            statement.executeUpdate(query);
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        }
//    }

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

    public Currency getCurrency(String code) {
        String query = "SELECT id, code, full_name, sign FROM currencies WHERE code = ?;";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, code);

            ResultSet rs = statement.executeQuery();
            Currency currency = null;

            if (rs != null) {
                int id = rs.getInt("id");
                String fullName = rs.getString("full_name");
                String sign = rs.getString("sign");
                currency = new Currency(id, code, fullName, sign);
            }

            return currency;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
