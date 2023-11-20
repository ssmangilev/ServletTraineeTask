package org.example.db;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class ConnectionManagerTest {
    private final PropertyLoader propertyLoader = PropertyLoader.getInstance();
    @InjectMocks
    ConnectionManager connectionManager = new ConnectionManagerImpl(propertyLoader);

    @Test
    void getConnection() throws SQLException {
        //Given

        //When
        Connection connection= connectionManager.getConnection();

        //Then
        assertThat(connection).isNotNull();
    }
}