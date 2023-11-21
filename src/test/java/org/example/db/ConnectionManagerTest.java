package org.example.db;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ConnectionManagerTest {
    private final PropertyLoader propertyLoader = Mockito.mock(PropertyLoader.class);
    @InjectMocks
    ConnectionManager connectionManager = new ConnectionManagerImpl(propertyLoader);

    static String connectionUrl;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:latest")
            .withInitScript("testscript.sql");

    @BeforeAll
    static void beforeAll() {
        postgres.start();

        String[] url = postgres.getJdbcUrl().split("\\?");
        connectionUrl = url[0];
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Test
    void getConnection() throws SQLException {

        //Given
        when(propertyLoader.getUrl()).thenReturn(connectionUrl);
        when(propertyLoader.getLogin()).thenReturn(postgres.getUsername());
        when(propertyLoader.getPassword()).thenReturn(postgres.getPassword());

        //When
        Connection connection = connectionManager.getConnection();

        //Then
        assertThat(connection).isNotNull();
    }
}