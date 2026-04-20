package org.example.oopjavagroupproject.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Handles database connections for the application.
 * This class reads connection properties from a 'db.properties' file
 * and provides a singleton-like connection object.
 *
 * @author Bohdan Dmytrenko, Bohdan Ruban, Olha Sribna
 * @version 1.0
 */
public class DatabaseHandler {
    private static Connection dbConnection;

    /**
     * Gets the active database connection.
     * If a connection does not exist or is closed, it creates a new one
     * using credentials from the 'db.properties' file.
     *
     * @return A Connection object to the database.
     * @throws SQLException if a database access error occurs.
     * @throws RuntimeException if the 'db.properties' file cannot be found.
     */
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
            // In a real application, a more robust logging framework should be used.
            e.printStackTrace();
            throw new SQLException("Failed to load db.properties", e);
        }

        String connectionString = props.getProperty("db.url");
        dbConnection = DriverManager.getConnection(connectionString, props.getProperty("db.user"), props.getProperty("db.password"));

        return dbConnection;
    }
}
