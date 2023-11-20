package org.example.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface for {@link ResultSet} to {@link T} mapping.
 * @param <T> any of interface implementations.
 */
public interface ResultSetMapper<T> {
    /**
     * Maps one entity from {@link ResultSet}.
     * @param resultSet query result.
     * @return {@link T}
     * @throws SQLException .
     */
    T map(ResultSet resultSet) throws SQLException;

    /**
     * Maps bundle of entities from {@link ResultSet}.
     * @param resultSet query result.
     * @return list of {@link T}.
     * @throws SQLException .
     */
    List<T> mapAll(ResultSet resultSet) throws SQLException;
}
