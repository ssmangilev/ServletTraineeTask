package org.example.repository.impl;

import org.example.db.ConnectionManager;
import org.example.exceptions.TraineeServletException;
import org.example.repository.mapper.ResultSetMapper;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Parent class for all repositories.
 * Template method realization.
 *
 * @param <T> any of repository
 */
public abstract class BaseRepository<T> {

    protected final ConnectionManager connectionManager;
    protected final ResultSetMapper<T> resultSetMapper;
    protected final String tableName;

    protected BaseRepository(ConnectionManager connectionManager, ResultSetMapper<T> resultSetMapper, String tableName) {
        this.connectionManager = connectionManager;
        this.resultSetMapper = resultSetMapper;
        this.tableName = tableName;
    }

    /**
     * Finds {@link T} by ID in the database.
     *
     * @param id to find.
     * @return {@link T} if present.
     */
    public T findById(UUID id) {
        String sql = "SELECT * FROM " + tableName + " WHERE " + getPrimaryKeyColumnName() + " = ?";
        return executeQueryForSingleResult(sql, id);
    }

    /**
     * Deletes  {@link T} by ID in the database.
     *
     * @param id to delete.
     * @return true if delete is successful.
     */
    public boolean deleteById(UUID id) {
        String sql = "DELETE FROM " + tableName + " WHERE " + getPrimaryKeyColumnName() + " = ?";
        return executeUpdate(sql, id) > 0;
    }

    /**
     * Finds all {@link T} in database.
     *
     * @return List of {@link T}.
     */
    public List<T> findAll() {
        String sql = "SELECT * FROM " + tableName;
        return executeQueryForList(sql);
    }

    /**
     * Saves new {@link T} in database.
     *
     * @param entity to save.
     * @return saved {@link T}
     */
    public T save(T entity) {
        String sql = buildInsertSql();
        executeUpdate(sql, getInsertValues(entity));
        return entity;
    }

    /**
     * Updates values of {@link T} in database.
     *
     * @param entity to update.
     * @return updated {@link T}
     */
    public T update(T entity) {
        List<Object> params = new ArrayList<>();
        String sql = buildUpdateSql(entity, params);
        executeUpdate(sql, params.toArray());
        return entity;
    }

    /**
     * Gets name of column with primary key for any {@link T}.
     *
     * @return name as string.
     */
    protected abstract String getPrimaryKeyColumnName();

    /**
     * Gets INSERT query string for any {@link T}.
     *
     * @return query template as string.
     */
    protected abstract String buildInsertSql();

    /**
     * Prepare income {@link T} fields for INSERT query.
     *
     * @param entity to insert.
     * @return object list for {@link PreparedStatement}.
     */
    protected abstract Object[] getInsertValues(T entity);

    /**
     * Creates INSERT query with a variable amount parameters.
     *
     * @param entity to update.
     * @param params list for storing parameters.
     * @return query for {@link PreparedStatement} as string.
     */
    protected abstract String buildUpdateSql(T entity, List<Object> params);

    /**
     * Executes {@link #findById(UUID)} query to database.
     *
     * @param sql query as string
     * @param id  ID to find
     * @return {@link T}
     */
    protected T executeQueryForSingleResult(String sql, UUID id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.map(resultSet);
        } catch (SQLException e) {
            throw new TraineeServletException(e.getMessage());
        }
    }

    /**
     * Executes {@link #findAll()} query to database.
     *
     * @param sql query as string
     * @return list of {@link T}
     */
    protected List<T> executeQueryForList(String sql) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetMapper.mapAll(resultSet);
        } catch (SQLException e) {
            throw new TraineeServletException(e.getMessage());
        }
    }

    /**
     * Executes update for {@link #save(T)}, {@link #update(T)}, {@link #deleteById(UUID)}.
     *
     * @param sql    query
     * @param params for {@link PreparedStatement}
     * @return count of updated rows
     */
    protected int executeUpdate(String sql, Object... params) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setParameters(preparedStatement, params);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new TraineeServletException(e.getMessage());
        }
    }

    /**
     * Sets parameters to {@link PreparedStatement} query.
     *
     * @param preparedStatement query
     * @param params            to set
     */
    private void setParameters(PreparedStatement preparedStatement, Object... params) {
        for (int i = 0; i < params.length; i++) {
            try {
                preparedStatement.setObject(i + 1, params[i]);
            } catch (SQLException e) {
                throw new TraineeServletException(e.getMessage());
            }
        }
    }
}