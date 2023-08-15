package com.dzvonik;

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

@WebServlet(urlPatterns = "/currencies")
public class CurrenciesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Currency> currencies = null;

        try {
            DbHandler db = DbHandler.getInstance();
            currencies = db.getCurrencies();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        String currenciesJson = gson.toJson(currencies);
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        writer.write(currenciesJson);
        writer.flush();
    }

}
