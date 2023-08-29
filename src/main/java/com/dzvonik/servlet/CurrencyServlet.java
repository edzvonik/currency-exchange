package com.dzvonik.servlet;

import com.dzvonik.model.Currency;
import com.dzvonik.model.dto.ErrorResponse;
import com.dzvonik.repository.CurrencyRepository;
import com.dzvonik.repository.JdbcCurrencyRepositoryImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(urlPatterns = "/currency")
public class CurrencyServlet extends HttpServlet {

    private final CurrencyRepository currencyRepository = new JdbcCurrencyRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        String jsonResponse;

        try {
            Optional currency = currencyRepository.findByCode(code);

            if (currency.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_NOT_FOUND, "This currency is not in the database");
                jsonResponse = gson.toJson(errorResponse);
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                jsonResponse = gson.toJson(currency);
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            jsonResponse = gson.toJson(errorResponse);
        }

        writer.write(jsonResponse);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        String jsonResponse;

        String code = req.getParameter("code");
        String fullName = req.getParameter("fullName");
        String sign = req.getParameter("sign");

        if (fullName == null || fullName.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "Missing parameter - fullName");
            jsonResponse = gson.toJson(errorResponse);
            writer.write(jsonResponse);
            return;
        }

        if (code == null || code.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "Missing parameter - code");
            jsonResponse = gson.toJson(errorResponse);
            writer.write(jsonResponse);
            return;
        }

        if (sign == null || sign.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "Missing parameter - sign");
            jsonResponse = gson.toJson(errorResponse);
            writer.write(jsonResponse);
            return;
        }

        Currency newCurrency = new Currency(code, fullName, sign);

        try {
            newCurrency = (Currency) currencyRepository.save(newCurrency);
            jsonResponse = gson.toJson(newCurrency);
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            jsonResponse = gson.toJson(errorResponse);
        }

        writer.write(jsonResponse);
        writer.flush();
    }

}
