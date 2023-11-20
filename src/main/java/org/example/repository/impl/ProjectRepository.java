package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.ProjectEntity;
import org.example.repository.mapper.ProjectResultSetMapper;

import java.util.List;
import java.util.UUID;

/**
 * Child class of {@link BaseRepository}.
 * Work with {@link ProjectEntity}. Methods description in the parent class.
 *
 * @see org.example.repository.impl.BaseRepository
 */
public class ProjectRepository extends BaseRepository<ProjectEntity> {

    public ProjectRepository(ConnectionManager connectionManager, ProjectResultSetMapper projectResultSetMapper) {
        super(connectionManager, projectResultSetMapper, "trainee_servlet_task.projects");
    }

    @Override
    public List<ProjectEntity> findAll() {
        return super.executeQueryForList(getAllSqlRequest());
    }

    @Override
    public ProjectEntity findById(UUID id) {
        String sql = getAllSqlRequest() + " where first.project_id = ?";
        return super.executeQueryForSingleResult(sql, id);
    }

    private String getAllSqlRequest() {
        return "SELECT first.project_id, first.project_name, first.project_start_date, first.project_deadline_date," +
                "p.performer_id, p.performer_name, p.performer_email, p.performer_role, t.task_id," +
                "t.task_name, t.task_description, t.task_priority, t.task_status, t.task_deadline" +
                " from trainee_servlet_task.projects first" +
                " LEFT JOIN trainee_servlet_task.project_performers pp on first.project_id = pp.project_id" +
                " LEFT JOIN trainee_servlet_task.performers p on p.performer_id = pp.performer_id" +
                " LEFT JOIN trainee_servlet_task.tasks t on p.performer_id = t.task_performer";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "project_id";
    }

    @Override
    protected String buildInsertSql() {
        return "INSERT INTO trainee_servlet_task.projects (project_id, project_name, project_start_date, " +
                "project_deadline_date) VALUES (?,?,?,?)";
    }

    @Override
    protected Object[] getInsertValues(ProjectEntity entity) {
        return new Object[]{
                entity.getProjectId(),
                entity.getProjectName(),
                entity.getProjectStartDate(),
                entity.getProjectDeadlineDate()
        };
    }

    @Override
    protected String buildUpdateSql(ProjectEntity entity, List<Object> params) {
        StringBuilder sql = new StringBuilder("UPDATE trainee_servlet_task.projects SET ");

        if (entity.getProjectName() != null) {
            sql.append("project_name = ?, ");
            params.add(entity.getProjectName());
        }
        if (entity.getProjectStartDate() != null) {
            sql.append("project_start_date = ?, ");
            params.add(entity.getProjectStartDate());
        }
        if (entity.getProjectDeadlineDate() != null) {
            sql.append("project_deadline_date = ?, ");
            params.add(entity.getProjectDeadlineDate());
        }

        if (params.isEmpty()) {
            return "";
        }

        sql.setLength(sql.length() - 2);

        sql.append(" WHERE project_id = ?");
        params.add(entity.getProjectId());
        return sql.toString();
    }
}

