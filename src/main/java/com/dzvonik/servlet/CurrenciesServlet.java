package com.dzvonik.servlet;

import com.dzvonik.model.Currency;
import com.dzvonik.model.dto.ErrorResponse;
import com.dzvonik.repository.CurrencyRepository;
import com.dzvonik.repository.JdbcCurrencyRepositoryImpl;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/currencies")
public class CurrenciesServlet extends HttpServlet {

    private final CurrencyRepository currencyRepository = new JdbcCurrencyRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();
        String jsonResponse;

        try {
            List<Currency> currencies = currencyRepository.findAll();

            if (currencies.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_NOT_FOUND, "Currencies are not in the database");
                jsonResponse = gson.toJson(errorResponse);
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                jsonResponse = gson.toJson(currencies);
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            jsonResponse = gson.toJson(errorResponse);
        }

        writer.write(jsonResponse);
        writer.flush();
    }

}
