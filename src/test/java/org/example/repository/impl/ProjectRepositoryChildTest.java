package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.db.ConnectionManagerImpl;
import org.example.model.ProjectEntity;
import org.example.repository.mapper.ProjectResultSetMapper;
import org.example.repository.mapper.ProjectResultSetMapperImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.*;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;

@SuppressWarnings("ALL")
class ProjectRepositoryChildTest {

    ConnectionManager connectionManager = Mockito.mock(ConnectionManagerImpl.class);

    ProjectResultSetMapper resultSetMapper = new ProjectResultSetMapperImpl();
    @InjectMocks
    ProjectRepositoryChild repository = new ProjectRepositoryChild(connectionManager, resultSetMapper);

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
        ProjectEntity entity = new ProjectEntity();
        entity.setProjectId(uuid);
        entity.setProjectName("project");

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        ProjectEntity saved = repository.save(entity);
        boolean actual = repository.deleteById(saved.getProjectId());

        //Then
        assertThat(saved).isNotNull();
        assertThat(actual).isTrue();
    }

    @Test
    void should_return_saved_entity() throws SQLException {

        //Given
        UUID uuid = UUID.randomUUID();
        ProjectEntity entity = new ProjectEntity();
        entity.setProjectId(uuid);
        entity.setProjectName("project");

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        repository.save(entity);
        ProjectEntity actual = repository.findById(uuid);

        //Then
        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("projectId", uuid)
                .hasFieldOrPropertyWithValue("projectName", "project");
    }

    @Test
    void should_return_updated_entity() throws SQLException {

        //Given
        UUID uuid = UUID.randomUUID();
        ProjectEntity entity = new ProjectEntity();
        entity.setProjectId(uuid);
        entity.setProjectName("project");

        ProjectEntity changed = new ProjectEntity();
        changed.setProjectId(uuid);
        changed.setProjectName("qwerty");

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        repository.save(entity);
        repository.update(changed);
        ProjectEntity actual = repository.findById(uuid);

        //Then
        assertThat(actual).hasFieldOrPropertyWithValue("projectName", "qwerty");
    }

    @Test
    void should_return_list_of_entity() throws SQLException {

        //Given
        UUID uuid = UUID.randomUUID();
        ProjectEntity entity = new ProjectEntity();
        entity.setProjectId(uuid);
        entity.setProjectName("project");

        UUID uuid2 = UUID.randomUUID();
        ProjectEntity entity2 = new ProjectEntity();
        entity2.setProjectId(uuid2);
        entity2.setProjectName("project2");

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        repository.save(entity);
        repository.save(entity2);
        List<ProjectEntity> actual = repository.findAll();

        //Then
        assertThat(actual)
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    void should_return_entity_that_finds_by_Id() throws SQLException {

        //Given
        UUID uuid = UUID.randomUUID();
        ProjectEntity entity = new ProjectEntity();
        entity.setProjectId(uuid);
        entity.setProjectName("project");

        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        //When
        repository.save(entity);
        ProjectEntity actual = repository.findById(uuid);

        //Then
        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("projectId", uuid)
                .hasFieldOrPropertyWithValue("projectName", "project");
    }

    @Test
    void should_return_entity_with_all_fields() throws SQLException {

        //Given
        doAnswer(invocation -> DriverManager.getConnection(
                connectionUrl,
                postgres.getUsername(),
                postgres.getPassword())).when(connectionManager).getConnection();

        UUID testUuid = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getUuidForTest())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                testUuid = (UUID) resultSet.getObject("project_id");
            }
        }

        //When
        ProjectEntity actual = repository.findById(testUuid);

        //Then
        assertThat(actual).hasNoNullFieldsOrProperties();
    }

    private String getUuidForTest() {
        return "SELECT project_id from trainee_servlet_task.projects where project_name = 'Проект1';";
    }
}