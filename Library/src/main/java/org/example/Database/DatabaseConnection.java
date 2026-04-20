package org.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/Library";
    private static final String USER = "postgres";
    private static final String PASSWORD = "mango";

    public static Connection getConnection() throws SQLException {
        try {
            // Force the JVM to find and load the Postgres Driver class
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found in classpath!", e);
        }

        System.out.println("tttt");

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
