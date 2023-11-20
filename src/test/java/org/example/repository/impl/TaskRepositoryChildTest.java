package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.exceptions.TraineeServletException;
import org.example.model.PerformerEntity;
import org.example.model.ProjectEntity;
import org.example.model.TaskEntity;
import org.example.repository.mapper.TaskResultSetMapper;
import org.example.repository.mapper.TaskResultSetMapperImpl;
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
import static org.mockito.Mockito.doAnswer;

class TaskRepositoryChildTest {

    ConnectionManager connectionManager = Mockito.mock(ConnectionManagerImpl.class);

    TaskResultSetMapper resultSetMapper = new TaskResultSetMapperImpl();
    @InjectMocks
    TaskRepositoryChild repository = new TaskRepositoryChild(connectionManager, resultSetMapper);

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
        TaskEntity entity = new TaskEntity();
        entity.setTaskId(uuid);
        entity.setTaskName("firstTask");
        entity.setTaskProject(new ProjectEntity());
        entity.setTaskPerformer(new PerformerEntity());

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        TaskEntity saved = repository.save(entity);
        boolean actual = repository.deleteById(saved.getTaskId());

        //Then
        assertThat(saved).isNotNull();
        assertThat(actual).isTrue();
    }

    @Test
    void should_return_saved_entity() throws SQLException {

        //Given
        UUID uuid = UUID.randomUUID();
        TaskEntity entity = new TaskEntity();
        entity.setTaskId(uuid);
        entity.setTaskName("firstTask");
        entity.setTaskProject(new ProjectEntity());
        entity.setTaskPerformer(new PerformerEntity());

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        repository.save(entity);
        TaskEntity actual = repository.findById(uuid);

        //Then
        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("taskId", uuid)
                .hasFieldOrPropertyWithValue("taskName", "firstTask");
    }

    @Test
    void should_return_updated_entity() throws SQLException {

        //Given
        UUID uuid = UUID.randomUUID();
        TaskEntity entity = new TaskEntity();
        entity.setTaskId(uuid);
        entity.setTaskName("firstTask");
        entity.setTaskProject(new ProjectEntity());
        entity.setTaskPerformer(new PerformerEntity());


        TaskEntity changed = new TaskEntity();
        changed.setTaskId(uuid);
        changed.setTaskName("changed");

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        repository.save(entity);
        repository.update(changed);
        TaskEntity actual = repository.findById(uuid);

        //Then
        assertThat(actual).hasFieldOrPropertyWithValue("taskName", "changed");
    }

    @Test
    void should_return_list_of_entity() throws SQLException {

        //Given
        UUID uuid = UUID.randomUUID();
        TaskEntity entity = new TaskEntity();
        entity.setTaskId(uuid);
        entity.setTaskName("firstTask");
        entity.setTaskProject(new ProjectEntity());
        entity.setTaskPerformer(new PerformerEntity());

        UUID uuid2 = UUID.randomUUID();
        TaskEntity entity2 = new TaskEntity();
        entity2.setTaskId(uuid2);
        entity2.setTaskName("changed");
        entity2.setTaskProject(new ProjectEntity());
        entity2.setTaskPerformer(new PerformerEntity());

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        repository.save(entity);
        repository.save(entity2);
        List<TaskEntity> actual = repository.findAll();

        //Then
        assertThat(actual)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    void should_return_entity_that_finds_by_Id() throws SQLException {

        //Given
        UUID uuid = UUID.randomUUID();
        TaskEntity entity = new TaskEntity();
        entity.setTaskId(uuid);
        entity.setTaskName("firstTask");
        entity.setTaskProject(new ProjectEntity());
        entity.setTaskPerformer(new PerformerEntity());

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        repository.save(entity);
        TaskEntity actual = repository.findById(uuid);

        //Then
        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("taskId", uuid)
                .hasFieldOrPropertyWithValue("taskName", "firstTask");
    }
}