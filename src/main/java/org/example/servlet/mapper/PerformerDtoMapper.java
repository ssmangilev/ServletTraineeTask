package org.example.servlet.mapper;

import org.example.model.PerformerEntity;
import org.example.servlet.dto.IncomingPerformerDto;
import org.example.servlet.dto.OutGoingPerformerDto;
import org.mapstruct.Mapper;

/**
 * Converts entity to DTO and back with <a href="https://mapstruct.org/">MapStruct</a>
 * Implementation - {@link PerformerDtoMapperImpl}
 */
@Mapper
public interface PerformerDtoMapper {
    PerformerEntity map(IncomingPerformerDto incomingPerformerDto);

    OutGoingPerformerDto map(PerformerEntity performerEntity);
}
