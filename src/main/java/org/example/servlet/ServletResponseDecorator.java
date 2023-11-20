package org.example.servlet;

import org.example.exceptions.TraineeServletException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletResponseDecorator implements Filter {

    private String encoding = "UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            request.setCharacterEncoding(encoding);

            response.setContentType("application/json");
            response.setCharacterEncoding(encoding);
            filterChain.doFilter(request, response);

        } catch (TraineeServletException e) {
            handleTraineeServletError((HttpServletResponse) response, e);
        } catch (Exception e) {
            handleError((HttpServletResponse) response, e);
        } finally {
            response.getWriter().close();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }
    }

    @Override
    public void destroy() {
        // clean up if needed
    }

    private void handleError(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }

    private void handleTraineeServletError(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    }
}