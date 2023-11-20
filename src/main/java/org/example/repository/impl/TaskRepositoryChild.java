package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.TaskEntity;
import org.example.repository.mapper.TaskResultSetMapper;

import java.util.List;

/**
 * Child class of {@link BaseRepository}.
 * Work with {@link TaskEntity}. Methods description in the parent class.
 *
 * @see org.example.repository.impl.BaseRepository
 */
public class TaskRepositoryChild extends BaseRepository<TaskEntity> {

    public TaskRepositoryChild(ConnectionManager connectionManager, TaskResultSetMapper taskResultSetMapper) {
        super(connectionManager, taskResultSetMapper, "trainee_servlet_task.tasks");
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "task_id";
    }

    @Override
    protected String buildInsertSql() {
        return "INSERT INTO trainee_servlet_task.tasks (task_id, task_name, task_description, task_priority, " +
                "task_status, task_deadline, task_performer, task_project) VALUES (?,?,?,?,?,?,?,?)";
    }

    @Override
    protected Object[] getInsertValues(TaskEntity entity) {
        return new Object[]{
                entity.getTaskId(),
                entity.getTaskName(),
                entity.getTaskDescription(),
                entity.getTaskPriority(),
                entity.getTaskStatus(),
                entity.getTaskDeadline(),
                entity.getTaskPerformer().getPerformerId(),
                entity.getTaskProject().getProjectId()
        };
    }

    @Override
    protected String buildUpdateSql(TaskEntity entity, List<Object> params) {
        StringBuilder sql = new StringBuilder("UPDATE trainee_servlet_task.tasks SET ");

        if (entity.getTaskName() != null) {
            sql.append("task_name = ?, ");
            params.add(entity.getTaskName());
        }
        if (entity.getTaskDescription() != null) {
            sql.append("task_description = ?, ");
            params.add(entity.getTaskDescription());
        }
        if (entity.getTaskPriority() != null) {
            sql.append("task_priority = ?, ");
            params.add(entity.getTaskPriority());
        }
        if (entity.getTaskStatus() != null) {
            sql.append("task_status = ?, ");
            params.add(entity.getTaskStatus());
        }
        if (entity.getTaskDeadline() != null) {
            sql.append("task_deadline = ?, ");
            params.add(entity.getTaskDeadline());
        }
        if (entity.getTaskPerformer() != null) {
            sql.append("task_performer = ?, ");
            params.add(entity.getTaskPerformer().getPerformerId());
        }
        if (entity.getTaskProject() != null) {
            sql.append("task_project = ?, ");
            params.add(entity.getTaskProject().getProjectId());
        }

        if (params.isEmpty()) {
            return "";
        }

        sql.setLength(sql.length() - 2);

        sql.append(" WHERE task_id = ?");
        params.add(entity.getTaskId());
        return sql.toString();
    }
}
