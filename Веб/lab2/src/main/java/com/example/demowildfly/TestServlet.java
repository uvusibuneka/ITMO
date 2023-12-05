package com.example.demowildfly;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "TestServlet", value = "/tes")
public class TestServlet extends HttpServlet {
    //simple example of a servlet

     public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
            response.getWriter().println("H!");

     }

        public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
                response.getWriter().println("H!" + request.getParameter("x"));
        }
}