package org.example.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface that create connection to database
 */
public interface ConnectionManager {

    /**
     * Get connection to user's database
     *
     * @return {@link Connection}
     */
    Connection getConnection() throws SQLException;
}
