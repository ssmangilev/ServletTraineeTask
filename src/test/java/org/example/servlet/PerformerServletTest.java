package org.example.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exceptions.TraineeServletException;
import org.example.service.impl.PerformerServiceImpl;
import org.example.servlet.dto.OutGoingPerformerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PerformerServletTest {

    PerformerServiceImpl service = Mockito.mock(PerformerServiceImpl.class);

    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    @InjectMocks
    PerformerServlet servlet = new PerformerServlet(service);


    @Test
    void doGetCorrectTestIfUuidIsPresent() throws IOException {
        //Given
        UUID testId = UUID.randomUUID();
        when(request.getParameter("uuid")).thenReturn(testId.toString());

        OutGoingPerformerDto mockDto = new OutGoingPerformerDto();
        mockDto.setPerformerId(testId.toString());
        when(service.findById(testId)).thenReturn(mockDto);

        PrintWriter writer = Mockito.mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // When
        servlet.doGet(request, response);

        // Then
        verify(writer).println(mockDto.toString());
    }

    @Test
    void should_throw_exception_getById() throws IOException {
        //Given
        String testId = "111";
        when(request.getParameter("uuid")).thenReturn(testId);

        PrintWriter writer = Mockito.mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // When

        // Then
        assertThrows(IllegalArgumentException.class, () -> servlet.doGet(request, response));
    }

    @Test
    void should_throw_exception_Id_notfound() throws IOException {
        //Given
        UUID testId = UUID.randomUUID();
        when(request.getParameter("uuid")).thenReturn(testId.toString());

        PrintWriter writer = Mockito.mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // When


        // Then
        assertThrows(Exception.class, () -> servlet.doGet(request, response));
    }

    @Test
    void doGetCorrectTestWhenUuidParameterNotProvided() throws IOException {
        // Given
        when(request.getParameter("uuid")).thenReturn(null);

        OutGoingPerformerDto mockDto = mock(OutGoingPerformerDto.class);
        when(service.findAll()).thenReturn(Collections.singletonList(mockDto));


        PrintWriter writer = Mockito.mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // When
        servlet.doGet(request, response);

        // Then
        verify(writer).println(Collections.singletonList(mockDto));
    }


    @Test
    void doPostForSaveCorrectTest() throws Exception {

        //Given
        UUID testId = UUID.randomUUID();

        OutGoingPerformerDto mockDto = new OutGoingPerformerDto();
        mockDto.setPerformerId(testId.toString());
        mockDto.setName("Ivan");
        mockDto.setEmail("ivan@gmail.com");
        mockDto.setRole("development");
        mockDto.setPerformerProjects(null);
        mockDto.setPerformerTasks(null);

        ServletInputStream mockServletInputStream = getMockServletInputStream();

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);


        when(request.getInputStream()).thenReturn(mockServletInputStream);
        when(response.getWriter()).thenReturn(writer);
        when(service.saveOrUpdate(any())).thenReturn(mockDto);

        // When
        servlet.doPost(request, response);
        String actual = stringWriter.getBuffer().toString().trim();

        // Then
        assertThat(actual).isEqualTo(mockDto.toString());
    }


    @Test
    void doPutForUpdateCorrectTest() throws IOException {

        //Given
        UUID testId = UUID.randomUUID();

        OutGoingPerformerDto mockDto = new OutGoingPerformerDto();
        mockDto.setPerformerId(testId.toString());
        mockDto.setName("Ivan");
        mockDto.setEmail("ivan@gmail.com");
        mockDto.setRole("development");
        mockDto.setPerformerProjects(null);
        mockDto.setPerformerTasks(null);

        ServletInputStream mockServletInputStream = getMockServletInputStream();

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);


        when(request.getInputStream()).thenReturn(mockServletInputStream);
        when(response.getWriter()).thenReturn(writer);
        when(service.saveOrUpdate(any())).thenReturn(mockDto);

        // When
        servlet.doPut(request, response);
        String actual = stringWriter.getBuffer().toString().trim();

        // Then
        assertThat(actual).isEqualTo(mockDto.toString());
    }

    @Test
    void doDeleteByIdCorrectTest() throws IOException {

        //Given
        UUID testId = UUID.randomUUID();
        when(request.getParameter("uuid")).thenReturn(testId.toString());
        boolean actual = true;
        when(service.delete(testId)).thenReturn(actual);

        PrintWriter writer = Mockito.mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        //When
        servlet.doDelete(request,response);

        //Then
        verify(writer).println(actual);
    }

    private static ServletInputStream getMockServletInputStream() throws IOException {
        String jsonInput = new ObjectMapper().writeValueAsString(Collections.EMPTY_MAP);
        byte[] myBinaryData = jsonInput.getBytes();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(myBinaryData);

        ServletInputStream mockServletInputStream = mock(ServletInputStream.class);

        when(mockServletInputStream.read(ArgumentMatchers.<byte[]>any(), anyInt(), anyInt())).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                byte[] output = (byte[]) args[0];
                int offset = (int) args[1];
                int length = (int) args[2];
                return byteArrayInputStream.read(output, offset, length);
            }
        });
        return mockServletInputStream;
    }
}
