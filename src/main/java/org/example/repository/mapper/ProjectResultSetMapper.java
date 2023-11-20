package org.example.repository.mapper;

import org.example.exceptions.TraineeServletException;
import org.example.model.ProjectEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @see org.example.repository.mapper.ResultSetMapper
 */
public interface ProjectResultSetMapper extends ResultSetMapper<ProjectEntity> {
    ProjectEntity map(ResultSet resultSet) throws SQLException;
    List<ProjectEntity> mapAll(ResultSet resultSet) throws SQLException;
}
