package org.example.service;

import org.example.model.TaskEntity;
import org.example.repository.impl.TaskRepositoryChild;
import org.example.service.impl.TaskServiceImpl;
import org.example.servlet.dto.IncomingTaskDto;
import org.example.servlet.dto.OutGoingTaskDto;
import org.example.servlet.mapper.TaskDtoMapper;
import org.example.servlet.mapper.TaskDtoMapperImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TaskServiceTest {
    private final TaskRepositoryChild repository = Mockito.mock(TaskRepositoryChild.class);
    private final TaskDtoMapper dtoMapper = Mockito.spy(TaskDtoMapperImpl.class);

    @InjectMocks
    TaskService service = new TaskServiceImpl(repository, dtoMapper);

    @Test
    void should_find_By_Id_and_return_TaskDto_CorrectTest() {
        //given
        UUID testId = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(createEntity());

        //when
        OutGoingTaskDto result = service.findById(testId);

        //then
        assertThat(result).isNotNull().isExactlyInstanceOf(OutGoingTaskDto.class);
    }

    @Test
    void should_findAll_and_return_List_of_TaskDto() {

        //Given
        when(repository.findAll()).thenReturn(new ArrayList<>());

        //When
        List<OutGoingTaskDto> actual = service.findAll();

        //Then
        assertThat(actual).isNotNull();

    }


    @Test
    void should_save_and_return_TaskDto() {
        //Given
        IncomingTaskDto given = new IncomingTaskDto();
        given.setTaskStatus("at WOrk");
        given.setTaskPriority("low");
        TaskEntity mockEntity = new TaskEntity();
        mockEntity.setTaskStatus("AT WORK");
        mockEntity.setTaskPriority("LOW");
        when(repository.save(any())).thenReturn(mockEntity);

        //When
        OutGoingTaskDto actual = service.saveOrUpdate(given);
        //Then
        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("taskStatus", "AT WORK")
                .hasFieldOrPropertyWithValue("taskPriority", "LOW");
    }

    @Test
    void should_update_and_return_PerformerDto() {

        //Given
        UUID uuid = UUID.randomUUID();
        IncomingTaskDto given = new IncomingTaskDto();
        given.setTaskId(uuid);
        given.setTaskStatus("at WOrk");
        given.setTaskPriority("low");
        TaskEntity mockEntity = new TaskEntity();
        mockEntity.setTaskId(uuid);
        mockEntity.setTaskStatus("AT WORK");
        mockEntity.setTaskPriority("LOW");
        when(repository.update(any())).thenReturn(mockEntity);

        //When
        OutGoingTaskDto actual = service.saveOrUpdate(given);

        //Then
        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("taskId", uuid.toString())
                .hasFieldOrPropertyWithValue("taskStatus", "AT WORK")
                .hasFieldOrPropertyWithValue("taskPriority", "LOW");
    }

    @Test
    void deleteByIdCorrectTest() {

        //Given
        UUID uuid = UUID.randomUUID();
        when(repository.deleteById(uuid)).thenReturn(true);

        //When
        boolean actual = service.delete(uuid);

        //Then
        assertThat(actual).isTrue();
    }

    private TaskEntity createEntity() {
        return new TaskEntity();
    }
}