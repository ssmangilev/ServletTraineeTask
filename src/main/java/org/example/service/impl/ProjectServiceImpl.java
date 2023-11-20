package org.example.service.impl;

import org.example.db.ConnectionManagerImpl;
import org.example.model.ProjectEntity;
import org.example.repository.impl.BaseRepository;
import org.example.repository.impl.ProjectRepository;
import org.example.repository.mapper.ProjectResultSetMapperImpl;
import org.example.service.PerformerService;
import org.example.service.ProjectService;
import org.example.servlet.dto.IncomingProjectDto;
import org.example.servlet.dto.OutGoingProjectDto;
import org.example.servlet.mapper.ProjectDtoMapper;
import org.example.servlet.mapper.ProjectDtoMapperImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * implements {@link PerformerService}
 *
 * @see org.example.service.PerformerService
 */
public class ProjectServiceImpl implements ProjectService {
    BaseRepository<ProjectEntity> repository;
    private final ProjectDtoMapper projectDtoMapper;

    /**
     * {@inheritDoc}
     *
     * @param uuid to find
     * @return {@inheritDoc}
     */
    @Override
    public OutGoingProjectDto findById(UUID uuid) {
        return projectDtoMapper.map(repository.findById(uuid));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public List<OutGoingProjectDto> findAll() {
        List<OutGoingProjectDto> projectDtoList = new ArrayList<>();
        for (ProjectEntity entity : repository.findAll()) {
            projectDtoList.add(
                    projectDtoMapper.map(entity)
            );
        }
        return projectDtoList;
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingProjectDto to save or update.
     * @return {@inheritDoc}
     */
    @Override
    public OutGoingProjectDto saveOrUpdate(IncomingProjectDto incomingProjectDto) {
        ProjectEntity entity;
        if (incomingProjectDto.getProjectId() == null) {
            incomingProjectDto.setProjectId(UUID.randomUUID());
            entity = repository.save(projectDtoMapper.map(incomingProjectDto));
        } else {
            entity = repository.update(projectDtoMapper.map(incomingProjectDto));
        }
        return projectDtoMapper.map(entity);
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

    public ProjectServiceImpl() {
        this.repository = new ProjectRepository(new ConnectionManagerImpl(), new ProjectResultSetMapperImpl());
        this.projectDtoMapper = new ProjectDtoMapperImpl();
    }

    public ProjectServiceImpl(BaseRepository<ProjectEntity> repository, ProjectDtoMapper projectDtoMapper) {
        this.repository = repository;
        this.projectDtoMapper = projectDtoMapper;
    }
}
