package org.example.service.impl;

import org.example.db.ConnectionManagerImpl;
import org.example.model.TaskEntity;
import org.example.model.dictionaries.TaskPriorityEnum;
import org.example.model.dictionaries.TaskStatusEnum;
import org.example.repository.impl.BaseRepository;
import org.example.repository.impl.TaskRepositoryChild;
import org.example.repository.mapper.TaskResultSetMapperImpl;
import org.example.service.PerformerService;
import org.example.service.TaskService;
import org.example.servlet.dto.IncomingTaskDto;
import org.example.servlet.dto.OutGoingTaskDto;
import org.example.servlet.mapper.TaskDtoMapper;
import org.example.servlet.mapper.TaskDtoMapperImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * implements {@link PerformerService}
 *
 * @see org.example.service.PerformerService
 */
public class TaskServiceImpl implements TaskService {
    private final BaseRepository<TaskEntity> taskRepository;
    private final TaskDtoMapper taskDtoMapper;

    /**
     * {@inheritDoc}
     *
     * @param uuid to find
     * @return {@inheritDoc}
     */
    @Override
    public OutGoingTaskDto findById(UUID uuid) {
        return taskDtoMapper.map(taskRepository.findById(uuid));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public List<OutGoingTaskDto> findAll() {
        List<OutGoingTaskDto> taskDtoList = new ArrayList<>();
        for (TaskEntity entity : taskRepository.findAll()) {
            taskDtoList.add(
                    taskDtoMapper.map(entity)
            );
        }
        return taskDtoList;
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingTaskDto to save or update.
     * @return {@inheritDoc}
     */
    @Override
    public OutGoingTaskDto saveOrUpdate(IncomingTaskDto incomingTaskDto) {
        TaskEntity entity;
        incomingTaskDto.setTaskPriority(setValidPriorityOrThrow(incomingTaskDto));
        incomingTaskDto.setTaskStatus(setValidStatusOrThrow(incomingTaskDto));
        if (incomingTaskDto.getTaskId() == null) {
            incomingTaskDto.setTaskId(UUID.randomUUID());
            entity = taskRepository.save(taskDtoMapper.map(incomingTaskDto));
        } else {
            entity = taskRepository.update(taskDtoMapper.map(incomingTaskDto));
        }
        return taskDtoMapper.map(entity);
    }

    /**
     * {@inheritDoc}
     *
     * @param uuid to delete
     * @return {@inheritDoc}
     */
    @Override
    public boolean delete(UUID uuid) {
        return taskRepository.deleteById(uuid);
    }

    public TaskServiceImpl() {
        this.taskRepository = new TaskRepositoryChild(new ConnectionManagerImpl(), new TaskResultSetMapperImpl());
        this.taskDtoMapper = new TaskDtoMapperImpl();
    }

    /**
     * Checks in dictionary {@link TaskPriorityEnum} if income field is valid.
     *
     * @param incomingTaskDto dto for validation.
     * @return correct value as string or throw an {@link IllegalArgumentException} exception.
     */
    private String setValidPriorityOrThrow(IncomingTaskDto incomingTaskDto) {
        return TaskPriorityEnum.checkValue(incomingTaskDto.getTaskPriority());
    }

    /**
     * Checks in dictionary {@link TaskStatusEnum} if income field is valid.
     *
     * @param incomingTaskDto dto for validation.
     * @return correct value as string or throw an {@link IllegalArgumentException} exception.
     */
    private String setValidStatusOrThrow(IncomingTaskDto incomingTaskDto) {
        return TaskStatusEnum.checkValue(incomingTaskDto.getTaskStatus());
    }

    public TaskServiceImpl(BaseRepository<TaskEntity> taskRepository, TaskDtoMapper taskDtoMapper) {
        this.taskRepository = taskRepository;
        this.taskDtoMapper = taskDtoMapper;
    }
}
