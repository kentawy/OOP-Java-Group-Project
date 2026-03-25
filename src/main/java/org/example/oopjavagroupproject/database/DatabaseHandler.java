package org.example.oopjavagroupproject.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseHandler {
    private static Connection dbConnection;

    public Connection getDbConnection() throws SQLException {
        if (dbConnection != null && !dbConnection.isClosed()) {
            return dbConnection;
        }

        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Could not be found db.properties");
            }
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String connectionString = props.getProperty("db.url");
        dbConnection = DriverManager.getConnection(connectionString, props.getProperty("db.user"), props.getProperty("db.password"));

        return dbConnection;
    }
}