package com.dzvonik.servlet;

import com.dzvonik.model.dto.ErrorResponse;
import com.google.gson.Gson;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebFilter("/currency")
public class CurrencyFilter extends HttpFilter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (httpRequest.getMethod().equals("POST")) {
            PrintWriter writer = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Gson gson = new Gson();
            String jsonResponse;

            String code = httpRequest.getParameter("code");
            String fullName = httpRequest.getParameter("fullName");
            String sign = httpRequest.getParameter("sign");
            List<String> missingValues = new ArrayList<>();

            if (!isValidField(code)) {
                missingValues.add("code");
            }

            if (!isValidField(fullName)) {
                missingValues.add("fullName");
            }

            if (!isValidField(sign)) {
                missingValues.add("sign");
            }

            if (missingValues.size() == 0) {
                chain.doFilter(request, response);
            } else {
                httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Missing values - " + missingValues.stream().collect(Collectors.joining(", ")));
                jsonResponse = gson.toJson(errorResponse);
                writer.write(jsonResponse);
                writer.flush();
            }
        }

        chain.doFilter(httpRequest, httpResponse);
    }

    private boolean isValidField(String fieldValue) {
        return fieldValue != null && !fieldValue.trim().isEmpty();
    }

}
