package org.example.repository.mapper;

import org.example.exceptions.TraineeServletException;
import org.example.model.PerformerEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Implements {@link ResultSet} mapping methods for {@link PerformerEntity}.
 */
public class PerformerResultSetMapperImpl implements PerformerResultSetMapper {
    /**
     * {@inheritDoc}
     *
     * @param resultSet query result.
     * @return
     * @throws SQLException
     */
    @Override
    public PerformerEntity map(ResultSet resultSet) throws SQLException {
        List<PerformerEntity> list;
        list = mapAll(resultSet);
        return list.get(0);
    }

    /**
     * {@inheritDoc}
     *
     * @param resultSet query result.
     * @return
     * @throws SQLException
     */
    @Override
    public List<PerformerEntity> mapAll(ResultSet resultSet) throws SQLException {
        Map<UUID, PerformerEntity> performerMap = new HashMap<>();
        while (resultSet.next()) {
            UUID performerId = UUID.fromString(resultSet.getString("performer_id"));
            PerformerEntity performerEntity = performerMap.get(performerId);
            if (performerEntity == null) {
                performerEntity = new PerformerEntity();
                performerEntity.setPerformerId(performerId);
                performerEntity.setName(resultSet.getString("performer_name"));
                performerEntity.setEmail(resultSet.getString("performer_email"));
                performerEntity.setRole(resultSet.getString("performer_role"));
                performerEntity.setPerformerProjects(new ArrayList<>());
                performerEntity.setPerformerTasks(new ArrayList<>());
                performerMap.put(performerId, performerEntity);
            }
        }
        if (performerMap.isEmpty()) {
            throw new TraineeServletException("Wrong query! Not found!");
        }
        return new ArrayList<>(performerMap.values());
    }
}
