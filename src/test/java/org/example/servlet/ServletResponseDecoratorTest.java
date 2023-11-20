package org.example.servlet;

import org.example.exceptions.TraineeServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;


class ServletResponseDecoratorTest {


    private final ServletRequest request = Mockito.mock(ServletRequest.class);

    private final ServletResponse response = Mockito.spy(HttpServletResponse.class);

    private final FilterChain filterChain = Mockito.spy(FilterChain.class);

    private final FilterConfig filterConfig = Mockito.mock(FilterConfig.class);

    @InjectMocks
    private ServletResponseDecorator servletResponseDecorator = new ServletResponseDecorator();


    @BeforeEach
    public void setUp() {
        servletResponseDecorator.init(filterConfig);
    }

    @Test
    void testDoFilter() throws Exception {
        //Given
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        //When
        servletResponseDecorator.doFilter(request, response, filterChain);

        //Then
        verify(request).setCharacterEncoding("UTF-8");
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterWithException() throws IOException, ServletException {

        //Given
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        doThrow(new TraineeServletException()).when(filterChain).doFilter(request, response);

        //When
        servletResponseDecorator.doFilter(request, response, filterChain);

        HttpServletResponse test = (HttpServletResponse) response;

        //Then
        verify(test).sendError(HttpServletResponse.SC_NOT_FOUND, "Wrong input!");
    }


    @Test
    void testDoFilterWithGeneralException() throws Exception {

        //Given
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        Exception exception = new RuntimeException("General Exception");
        doThrow(exception).when(filterChain).doFilter(request, response);

        //When
        servletResponseDecorator.doFilter(request, response, filterChain);

        HttpServletResponse test = (HttpServletResponse) response;

        //Then
        verify(test).sendError(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
    }
}