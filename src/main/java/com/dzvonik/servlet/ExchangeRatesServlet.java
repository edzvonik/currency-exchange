package com.dzvonik.servlet;

import com.dzvonik.model.ExchangeRate;
import com.dzvonik.model.dto.ErrorResponse;
import com.dzvonik.repository.ExchangeRateRepository;
import com.dzvonik.repository.JdbcExchangeRateRepositoryImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    private final ExchangeRateRepository exchangeRateRepository = new JdbcExchangeRateRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();
        String jsonResponse;

        try {
            List exchangeRates = exchangeRateRepository.findAll();

            if (exchangeRates.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_NOT_FOUND, "Exchange rates is missing");
                jsonResponse = gson.toJson(errorResponse);
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                jsonResponse = gson.toJson(exchangeRates);
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            jsonResponse = gson.toJson(errorResponse);
        }

        writer.write(jsonResponse);
    }

}
