package org.example.service;

import org.example.exceptions.TraineeServletException;
import org.example.model.PerformerEntity;
import org.example.repository.impl.PerformerRepositoryChild;
import org.example.service.impl.PerformerServiceImpl;
import org.example.servlet.dto.IncomingPerformerDto;
import org.example.servlet.dto.OutGoingPerformerDto;
import org.example.servlet.mapper.PerformerDtoMapperImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class PerformerServiceTest {


    private PerformerRepositoryChild repository = Mockito.mock(PerformerRepositoryChild.class);

    private PerformerDtoMapperImpl dtoMapper = Mockito.spy(PerformerDtoMapperImpl.class);

    @InjectMocks
    private PerformerService service = new PerformerServiceImpl(repository, dtoMapper);


    @Test
    void should_find_By_Id_and_return_Performer_dto_CorrectTest() {
        //given
        UUID testId = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(createEntity());

        //when
        OutGoingPerformerDto result = service.findById(testId);

        //then
        assertThat(result).isNotNull().isExactlyInstanceOf(OutGoingPerformerDto.class);
    }

    @Test
    void should_findAll_and_return_List_of_PerformerDto() {

        //Given
        when(repository.findAll()).thenReturn(new ArrayList<>());

        //When
        List<OutGoingPerformerDto> actual = service.findAll();

        //Then
        assertThat(actual).isNotNull();

    }

    @Test
    void should_save_and_return_PerformerDto() {
        //Given
        IncomingPerformerDto given = new IncomingPerformerDto();
        given.setRole("development");
        PerformerEntity mockEntity = new PerformerEntity();
        mockEntity.setRole("DEVELOPMENT");
        when(repository.save(any())).thenReturn(mockEntity);

        //When
        OutGoingPerformerDto actual = service.saveOrUpdate(given);
        //Then
        assertThat(actual).isNotNull().hasFieldOrPropertyWithValue("role", "DEVELOPMENT");
    }

    @Test
    void should_update_and_return_PerformerDto() {
        //Given
        UUID uuid = UUID.randomUUID();
        IncomingPerformerDto given = new IncomingPerformerDto();
        given.setPerformerId(uuid);
        given.setRole("development");
        PerformerEntity mockEntity = new PerformerEntity();
        mockEntity.setRole("DEVELOPMENT");
        when(repository.update(any())).thenReturn(mockEntity);

        //When
        OutGoingPerformerDto actual = service.saveOrUpdate(given);
        //Then
        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("role", "DEVELOPMENT");
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

    private PerformerEntity createEntity() {
        return new PerformerEntity();
    }
}