package org.example.Session;

import org.example.DI.Component;
import org.example.Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class Session {
    private final Connection connection;

    public Session() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    // Begin transaction
    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    // Commit transaction
    public void commit() throws SQLException {
        connection.commit();
    }

    // Rollback transaction
    public void rollback() throws SQLException {
        connection.rollback();
    }
}
