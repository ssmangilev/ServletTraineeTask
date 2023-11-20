package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * {@link ConnectionManager} implementation. Creates a connection with properties from {@link PropertyLoader}.
 */
public class ConnectionManagerImpl implements ConnectionManager {

    private final PropertyLoader propertyLoader;

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    propertyLoader.getUrl(),
                    propertyLoader.getLogin(),
                    propertyLoader.getPassword());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public ConnectionManagerImpl() {
        this.propertyLoader = PropertyLoader.getInstance();
    }

    public ConnectionManagerImpl(PropertyLoader propertyLoader) {
        this.propertyLoader = propertyLoader;
    }
}
