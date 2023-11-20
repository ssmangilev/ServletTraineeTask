package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.model.PerformerEntity;
import org.example.repository.mapper.PerformerResultSetMapper;

import java.util.List;

/**
 * Child class of {@link BaseRepository}.
 * Work with {@link PerformerEntity}. Methods description in the parent class.
 *
 * @see org.example.repository.impl.BaseRepository
 */
public class PerformerRepositoryChild extends BaseRepository<PerformerEntity> {
    public PerformerRepositoryChild(
            ConnectionManager connectionManager, PerformerResultSetMapper performerResultSetMapper) {
        super(connectionManager, performerResultSetMapper, "trainee_servlet_task.performers");
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "performer_id";
    }

    @Override
    protected String buildInsertSql() {
        return "INSERT INTO trainee_servlet_task.performers (performer_id, performer_name, performer_email, " +
                "performer_role) VALUES (?,?,?,?)";
    }

    @Override
    protected Object[] getInsertValues(PerformerEntity entity) {
        return new Object[]{
                entity.getPerformerId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRole()
        };
    }

    @Override
    protected String buildUpdateSql(PerformerEntity entity, List<Object> params) {
        StringBuilder sql = new StringBuilder("UPDATE trainee_servlet_task.performers SET ");

        if (entity.getName() != null) {
            sql.append("performer_name = ?, ");
            params.add(entity.getName());
        }
        if (entity.getEmail() != null) {
            sql.append("performer_email = ?, ");
            params.add(entity.getEmail());
        }
        if (entity.getRole() != null) {
            sql.append("performer_role = ?, ");
            params.add(entity.getRole());
        }
        if (params.isEmpty()) {
            return "";
        }

        sql.setLength(sql.length() - 2);

        sql.append(" WHERE performer_id = ?");
        params.add(entity.getPerformerId());
        return sql.toString();
    }
}
