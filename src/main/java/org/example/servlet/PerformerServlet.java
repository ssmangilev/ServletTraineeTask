package org.example.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exceptions.TraineeServletException;
import org.example.service.PerformerService;
import org.example.service.impl.PerformerServiceImpl;
import org.example.servlet.dto.IncomingPerformerDto;
import org.example.servlet.dto.OutGoingPerformerDto;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * Servlet for Performers. It supports GET, POST, PUT, DELETE Http requests.
 */
@WebServlet(name = "performerServlet", value = "/performers")
public class PerformerServlet extends HttpServlet {
    private final PerformerService performerService;


    /**
     * Responses to GET Http request for one or more objects.
     *
     * @param req  an {@link HttpServletRequest} object that
     *             contains the request the client has made
     *             of the servlet.
     * @param resp an {@link HttpServletResponse} object that
     *             contains the response the servlet sends
     *             to the client.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        if (req.getParameter("uuid") != null) {
            IncomingPerformerDto incomeDto = new IncomingPerformerDto();
            incomeDto.setPerformerId(UUID.fromString(req.getParameter("uuid")));
            OutGoingPerformerDto byId = performerService.findById(incomeDto.getPerformerId());
            out.println(byId.toString());
        } else {
            out.println(performerService.findAll());
        }
    }

    /**
     * Responses to POST Http request. Write a new object to database.
     *
     * @param req  an {@link HttpServletRequest} object that
     *             contains the request the client has made
     *             of the servlet.
     * @param resp an {@link HttpServletResponse} object that
     *             contains the response the servlet sends
     *             to the client.
     * @throws IOException if input/output error appears.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println(performerService.saveOrUpdate(mapHttpRequestToDto(req)));
    }

    /**
     * Responses to PUT Http request. Update object values in the database.
     *
     * @param req  the {@link HttpServletRequest} object that
     *             contains the request the client made of
     *             the servlet.
     * @param resp the {@link HttpServletResponse} object that
     *             contains the response the servlet returns
     *             to the client.
     * @throws IOException if input/output error appears.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println(performerService.saveOrUpdate(mapHttpRequestToDto(req)));
    }

    /**
     * Responses to DELETE Http request. Erase object from database.
     *
     * @param req  the {@link HttpServletRequest} object that
     *             contains the request the client made of
     *             the servlet.
     * @param resp the {@link HttpServletResponse} object that
     *             contains the response the servlet returns
     *             to the client.
     * @throws IOException if input/output error appears.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("uuid") != null) {
            IncomingPerformerDto incomeDto = new IncomingPerformerDto();
            incomeDto.setPerformerId(UUID.fromString(req.getParameter("uuid")));
            PrintWriter out = resp.getWriter();
            out.println(performerService.delete(incomeDto.getPerformerId()));
        }
    }

    /**
     * Private util method to map user input to dto
     *
     * @param request user input.
     * @return {@link IncomingPerformerDto}
     */
    IncomingPerformerDto mapHttpRequestToDto(HttpServletRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(request.getInputStream(), IncomingPerformerDto.class);
        } catch (IOException e) {
            throw new TraineeServletException("Wrong input! Check fields!");
        }
    }

    public PerformerServlet() {
        this.performerService = new PerformerServiceImpl();
    }

    public PerformerServlet(PerformerService performerService) {
        this.performerService = performerService;
    }
}
