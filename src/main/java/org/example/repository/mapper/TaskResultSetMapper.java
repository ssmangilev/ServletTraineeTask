package org.example.repository.mapper;

import org.example.model.TaskEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @see org.example.repository.mapper.ResultSetMapper
 */
public interface TaskResultSetMapper extends ResultSetMapper<TaskEntity> {
    TaskEntity map(ResultSet resultSet) throws SQLException;
    List<TaskEntity> mapAll(ResultSet resultSet) throws SQLException;
}
