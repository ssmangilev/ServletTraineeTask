package org.example.repository.mapper;

import org.example.exceptions.TraineeServletException;
import org.example.model.PerformerEntity;
import org.example.model.ProjectEntity;
import org.example.model.TaskEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Implements {@link ResultSet} mapping methods for {@link ProjectEntity}.
 */
public class ProjectResultSetMapperImpl implements ProjectResultSetMapper {
    /**
     * {@inheritDoc}
     *
     * @param resultSet query result.
     * @return
     * @throws SQLException
     */
    @Override
    public List<ProjectEntity> mapAll(ResultSet resultSet) throws SQLException {
        Map<UUID, ProjectEntity> projectMap = new HashMap<>();
        Map<UUID, PerformerEntity> performerMap = new HashMap<>();
        Map<UUID, TaskEntity> taskMap = new HashMap<>();

        while (resultSet.next()) {
            UUID projectId = UUID.fromString(resultSet.getString("project_id"));
            ProjectEntity projectEntity = projectMap.get(projectId);

            if (projectEntity == null) {
                projectEntity = new ProjectEntity();
                projectEntity.setProjectId(projectId);
                projectEntity.setProjectName(resultSet.getString("project_name"));
                projectEntity.setProjectStartDate(resultSet.getTimestamp("project_start_date"));
                projectEntity.setProjectDeadlineDate(resultSet.getTimestamp("project_deadline_date"));
                projectEntity.setProjectPerformers(new ArrayList<>());
                projectEntity.setProjectTasks(new ArrayList<>());
                projectMap.put(projectId, projectEntity);
            }
            if (resultSet.getString("performer_id") != null) {
                UUID performerId = UUID.fromString(resultSet.getString("performer_id"));
                PerformerEntity performerEntity = performerMap.get(performerId);

                if (performerEntity == null) {
                    performerEntity = new PerformerEntity();
                    performerEntity.setPerformerId(performerId);
                    performerEntity.setName(resultSet.getString("performer_name"));
                    performerEntity.setEmail(resultSet.getString("performer_email"));
                    performerEntity.setRole(resultSet.getString("performer_role"));
                    performerMap.put(performerId, performerEntity);
                }
                if (resultSet.getString("task_id") != null) {
                    UUID taskId = UUID.fromString(resultSet.getString("task_id"));
                    TaskEntity taskEntity = taskMap.get(taskId);

                    if (taskEntity == null) {
                        taskEntity = new TaskEntity();
                        taskEntity.setTaskId(taskId);
                        taskEntity.setTaskName(resultSet.getString("task_name"));
                        taskEntity.setTaskDescription(resultSet.getString("task_description"));
                        taskEntity.setTaskPriority(resultSet.getString("task_priority"));
                        taskEntity.setTaskStatus(resultSet.getString("task_status"));
                        taskEntity.setTaskDeadline(resultSet.getTimestamp("task_deadline"));
                        taskMap.put(taskId, taskEntity);
                    }

                    projectEntity.getProjectPerformers().add(performerEntity);
                    projectEntity.getProjectTasks().add(taskEntity);
                }
            }
        }
        if (projectMap.isEmpty()) {
            throw new TraineeServletException("Wrong query! Not found!");
        }
        return new ArrayList<>(projectMap.values());
    }

    /**
     * {@inheritDoc}
     *
     * @param resultSet query result.
     * @return
     * @throws SQLException
     */

    @Override
    public ProjectEntity map(ResultSet resultSet) throws SQLException {
        List<ProjectEntity> list = mapAll(resultSet);
        return list.get(0);
    }
}
