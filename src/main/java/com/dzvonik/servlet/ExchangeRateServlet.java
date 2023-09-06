package com.dzvonik.servlet;

import com.dzvonik.model.ExchangeRate;
import com.dzvonik.model.dto.ErrorResponse;
import com.dzvonik.repository.CrudRepository;
import com.dzvonik.repository.ExchangeRateRepository;
import com.dzvonik.repository.JdbcExchangeRateRepositoryImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(urlPatterns = "/exchangeRate")
public class ExchangeRateServlet extends HttpServlet {

    private final ExchangeRateRepository exchangeRateRepository = new JdbcExchangeRateRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String jsonResponse;
        String pair = req.getParameter("pair");
        String baseCurrencyCode = pair.substring(0, 3);
        String targetCurrencyCode = pair.substring(3, 6);

        try {
            Optional exchangeRate = exchangeRateRepository.findByPair(baseCurrencyCode, targetCurrencyCode);

            if (exchangeRate.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_NOT_FOUND, "This pair is not in the database");
                jsonResponse = gson.toJson(errorResponse);
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                jsonResponse = gson.toJson(exchangeRate);
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            jsonResponse = gson.toJson(errorResponse);
        }

        Writer writer = resp.getWriter();
        writer.write(jsonResponse);
    }

}
