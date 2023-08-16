package com.dzvonik;

import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceConfig {

    private static final PGSimpleDataSource INSTANCE = new PGSimpleDataSource();

    static {
        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");
        System.out.println(url);
        System.out.println(username);
        System.out.println(password);
        System.out.println(System.getenv("JAVA_HOME"));

        if (url == null || username == null || password == null) {
            try (InputStream input = DataSourceConfig.class.getResourceAsStream("/db.properties")) {
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

}
