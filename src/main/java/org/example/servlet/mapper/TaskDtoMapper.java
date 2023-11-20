package org.example.servlet.mapper;

import org.example.model.TaskEntity;
import org.example.servlet.dto.IncomingTaskDto;
import org.example.servlet.dto.OutGoingTaskDto;
import org.mapstruct.Mapper;

/**
 * Converts entity to DTO and back with <a href="https://mapstruct.org/">MapStruct</a>
 * Implementation - {@link TaskDtoMapperImpl}
 */
@Mapper
public interface TaskDtoMapper {
    TaskEntity map(IncomingTaskDto incomingDto);
    OutGoingTaskDto map(TaskEntity taskEntity);
}
