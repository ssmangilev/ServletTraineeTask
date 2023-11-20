package org.example.service.impl;

import org.example.db.ConnectionManagerImpl;
import org.example.model.PerformerEntity;
import org.example.model.dictionaries.PerformerRole;
import org.example.repository.impl.BaseRepository;
import org.example.repository.impl.PerformerRepositoryChild;
import org.example.repository.mapper.PerformerResultSetMapperImpl;
import org.example.service.PerformerService;
import org.example.servlet.dto.IncomingPerformerDto;
import org.example.servlet.dto.OutGoingPerformerDto;
import org.example.servlet.mapper.PerformerDtoMapper;
import org.example.servlet.mapper.PerformerDtoMapperImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * implements {@link PerformerService}
 *
 * @see org.example.service.PerformerService
 */
public class PerformerServiceImpl implements PerformerService {
    private final BaseRepository<PerformerEntity> repository;
    private final PerformerDtoMapper dtoMapper;

    /**
     * {@inheritDoc}
     *
     * @param uuid to find
     * @return {@inheritDoc}
     */
    @Override
    public OutGoingPerformerDto findById(UUID uuid) {
        return dtoMapper.map(repository.findById(uuid));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public List<OutGoingPerformerDto> findAll() {
        List<OutGoingPerformerDto> performerDtoList = new ArrayList<>();
        for (PerformerEntity entity : repository.findAll()) {
            performerDtoList.add(
                    dtoMapper.map(entity)
            );
        }
        return performerDtoList;
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingPerformerDto to save or update.
     * @return {@inheritDoc}
     */
    @Override
    public OutGoingPerformerDto saveOrUpdate(IncomingPerformerDto incomingPerformerDto) {
        PerformerEntity entity;
        incomingPerformerDto.setRole(setValidRoleOrThrow(incomingPerformerDto.getRole()));
        if (incomingPerformerDto.getPerformerId() == null) {
            incomingPerformerDto.setPerformerId(UUID.randomUUID());
            entity = repository.save(dtoMapper.map(incomingPerformerDto));
        } else {
            entity = repository.update(dtoMapper.map(incomingPerformerDto));
        }
        return dtoMapper.map(entity);
    }

    /**
     * Checks in dictionary {@link PerformerRole} if income field is valid.
     *
     * @param inputRole string for validation.
     * @return correct value as string or throw an {@link IllegalArgumentException} exception.
     */
    private String setValidRoleOrThrow(String inputRole){
        return PerformerRole.checkValue(inputRole);
    }

    /**
     * {@inheritDoc}
     *
     * @param uuid to delete
     * @return {@inheritDoc}
     */
    @Override
    public boolean delete(UUID uuid) {
        return repository.deleteById(uuid);
    }

    public PerformerServiceImpl() {
        this.repository = new PerformerRepositoryChild(new ConnectionManagerImpl(), new PerformerResultSetMapperImpl());
        this.dtoMapper = new PerformerDtoMapperImpl();
    }

    public PerformerServiceImpl(BaseRepository<PerformerEntity> repository, PerformerDtoMapper dtoMapper) {
        this.repository = repository;
        this.dtoMapper = dtoMapper;
    }
}
