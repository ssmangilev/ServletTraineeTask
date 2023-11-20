package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.exceptions.TraineeServletException;
import org.example.model.PerformerEntity;
import org.example.repository.mapper.PerformerResultSetMapper;
import org.example.repository.mapper.PerformerResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;

class PerformerRepositoryTest {

    ConnectionManager connectionManager = Mockito.mock(ConnectionManagerImpl.class);

    PerformerResultSetMapper resultSetMapper = new PerformerResultSetMapperImpl();
    @InjectMocks
    PerformerRepository repository = new PerformerRepository(connectionManager, resultSetMapper);

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
    void should_return_true_delete_ById() throws SQLException {

        //Given
        UUID uuid = UUID.randomUUID();
        PerformerEntity entity = new PerformerEntity();
        entity.setPerformerId(uuid);
        entity.setName("Ivan");

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        PerformerEntity saved = repository.save(entity);
        boolean actual = repository.deleteById(saved.getPerformerId());

        //Then
        assertThat(saved).isNotNull();
        assertThat(actual).isTrue();
    }

    @Test
    void should_return_saved_entity() throws SQLException {

        //Given
        UUID uuid = UUID.randomUUID();
        PerformerEntity entity = new PerformerEntity();
        entity.setPerformerId(uuid);
        entity.setName("Ivan");

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        repository.save(entity);
        PerformerEntity actual = repository.findById(uuid);

        //Then
        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("performerId", uuid)
                .hasFieldOrPropertyWithValue("name", "Ivan");
    }

    @Test
    void should_return_updated_entity() throws SQLException {

        //Given
        UUID uuid = UUID.randomUUID();
        PerformerEntity entity = new PerformerEntity();
        entity.setPerformerId(uuid);
        entity.setName("Ivan");

        PerformerEntity changed = new PerformerEntity();
        changed.setPerformerId(uuid);
        changed.setName("Vasya");

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        repository.save(entity);
        repository.update(changed);
        PerformerEntity actual = repository.findById(uuid);

        //Then
        assertThat(actual).hasFieldOrPropertyWithValue("name", "Vasya");
    }

    @Test
    void should_return_list_of_entity() throws SQLException {

        //Given
        UUID uuid = UUID.randomUUID();
        PerformerEntity entity = new PerformerEntity();
        entity.setPerformerId(uuid);
        entity.setName("Ivan");

        UUID uuid2 = UUID.randomUUID();
        PerformerEntity entity2 = new PerformerEntity();
        entity2.setPerformerId(uuid2);
        entity2.setName("John");

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        repository.save(entity);
        repository.save(entity2);
        List<PerformerEntity> actual = repository.findAll();

        //Then
        assertThat(actual)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    void should_return_entity_that_finds_by_Id() throws SQLException {

        //Given
        UUID uuid = UUID.randomUUID();
        PerformerEntity entity = new PerformerEntity();
        entity.setPerformerId(uuid);
        entity.setName("Ivan");

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        repository.save(entity);
        PerformerEntity actual = repository.findById(uuid);

        //Then
        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("performerId", uuid)
                .hasFieldOrPropertyWithValue("name", "Ivan");
    }

    @Test
    void should_throw_custom_exception_getById() throws SQLException {
        //Given
        UUID uuid= UUID.fromString("3bbf370e-a326-4fe9-b75c-46c00314d0e");

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        // When

        // Then
       assertThrows(TraineeServletException.class,()-> repository.findById(uuid));
    }
}