package org.example.service;

import org.example.exceptions.TraineeServletException;
import org.example.model.ProjectEntity;
import org.example.repository.impl.ProjectRepositoryChild;
import org.example.service.impl.ProjectServiceImpl;
import org.example.servlet.dto.IncomingProjectDto;
import org.example.servlet.dto.OutGoingProjectDto;
import org.example.servlet.mapper.ProjectDtoMapperImpl;
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


class ProjectServiceTest {
    ProjectRepositoryChild repository = Mockito.mock(ProjectRepositoryChild.class);
    ProjectDtoMapperImpl dtoMapper = Mockito.spy(ProjectDtoMapperImpl.class);
    @InjectMocks
    ProjectService service = new ProjectServiceImpl(repository, dtoMapper);

    @Test
    void should_find_By_Id_and_return_ProjectDto_Correct_Test() {

        //Given
        UUID testId = UUID.randomUUID();
        when(repository.findById(any())).thenReturn(createEntity());

        //When
        OutGoingProjectDto result = service.findById(testId);

        //Then
        assertThat(result).isNotNull().isExactlyInstanceOf(OutGoingProjectDto.class);
    }


    @Test
    void should_findAll_and_return_List_of_ProjectDto() {

        //Given
        when(repository.findAll()).thenReturn(new ArrayList<>());

        //When
        List<OutGoingProjectDto> actual = service.findAll();

        //Then
        assertThat(actual).isNotNull();
    }

    @Test
    void should_save_and_return_ProjectDto() {

        //Given
        IncomingProjectDto given = new IncomingProjectDto();
        given.setProjectName("best project");

        ProjectEntity mockEntity = new ProjectEntity();
        mockEntity.setProjectName("best project");

        when(repository.save(any())).thenReturn(mockEntity);

        //When
        OutGoingProjectDto actual = service.saveOrUpdate(given);

        //Then
        assertThat(actual).isNotNull().hasFieldOrPropertyWithValue("projectName", "best project");
    }

    @Test
    void should_update_and_return_ProjectDto() {

        //Given
        UUID uuid = UUID.randomUUID();
        IncomingProjectDto given = new IncomingProjectDto();
        given.setProjectId(uuid);
        given.setProjectName("best project");

        ProjectEntity mockEntity = new ProjectEntity();
        mockEntity.setProjectId(uuid);
        mockEntity.setProjectName("best project");

        when(repository.update(any())).thenReturn(mockEntity);

        //When
        OutGoingProjectDto actual = service.saveOrUpdate(given);

        //Then
        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("projectName", "best project")
                .hasFieldOrPropertyWithValue("projectId", uuid.toString());
    }

    @Test
    void should_delete_and_return_true() {

        //Given
        UUID uuid = UUID.randomUUID();
        when(repository.deleteById(uuid)).thenReturn(true);

        //When
        boolean actual = service.delete(uuid);

        //Then
        assertThat(actual).isTrue();
    }

    private ProjectEntity createEntity() {
        return new ProjectEntity();
    }
}