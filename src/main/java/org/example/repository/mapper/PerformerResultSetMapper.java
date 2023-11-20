package org.example.repository.mapper;

import org.example.model.PerformerEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @see org.example.repository.mapper.ResultSetMapper
 */
public interface PerformerResultSetMapper extends ResultSetMapper<PerformerEntity>{
    PerformerEntity map(ResultSet resultSet) throws SQLException;
    List<PerformerEntity> mapAll(ResultSet resultSet) throws SQLException;
}
