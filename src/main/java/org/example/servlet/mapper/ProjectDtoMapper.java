package org.example.servlet.mapper;

import org.example.model.ProjectEntity;
import org.example.servlet.dto.IncomingProjectDto;
import org.example.servlet.dto.OutGoingProjectDto;
import org.mapstruct.Mapper;

/**
 * Converts entity to DTO and back with <a href="https://mapstruct.org/">MapStruct</a>
 * Implementation - {@link ProjectDtoMapperImpl}
 */
@Mapper
public interface ProjectDtoMapper {
    ProjectEntity map(IncomingProjectDto incomingProjectDto);

    OutGoingProjectDto map(ProjectEntity projectEntity);
}
