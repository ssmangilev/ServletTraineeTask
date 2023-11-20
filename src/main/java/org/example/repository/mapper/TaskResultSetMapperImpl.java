package org.example.repository.mapper;

import org.example.exceptions.TraineeServletException;
import org.example.model.TaskEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Implements {@link ResultSet} mapping methods for {@link TaskEntity}.
 */
public class TaskResultSetMapperImpl implements TaskResultSetMapper {
    /**
     * {@inheritDoc}
     * @param resultSet query result.
     * @return
     * @throws SQLException
     */
    @Override
    public TaskEntity map(ResultSet resultSet) throws SQLException {
        List<TaskEntity> list = mapAll(resultSet);
        return list.get(0);
    }

    /**
     * {@inheritDoc}
     * @param resultSet query result.
     * @return
     * @throws SQLException
     */
    @Override
    public List<TaskEntity> mapAll(ResultSet resultSet) throws SQLException {
        Map<UUID, TaskEntity> taskMap = new HashMap<>();
        while (resultSet.next()) {
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
        }
        if (taskMap.isEmpty()) {
            throw new TraineeServletException("Wrong query! Not found!");
        }
        return new ArrayList<>(taskMap.values());
    }
}
