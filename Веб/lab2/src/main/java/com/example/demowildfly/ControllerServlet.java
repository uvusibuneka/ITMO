package com.example.demowildfly;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.util.*;

import static java.lang.Math.round;

@WebServlet(name = "controllerServlet", value = "/controller")
public class ControllerServlet extends HttpServlet {
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String forwardPath = getServletContext().getContextPath();

        if (request.getParameter("clear") != null) {
            final HttpSession session = request.getSession();
            session.invalidate();
        }

        if (request.getParameter("x") != null
                && request.getParameter("y") != null
                && request.getParameter("r") != null)
        {
            try {
                if (request.getParameter("r").length() > 8 ||
                    request.getParameter("x").length() > 8 ||
                    request.getParameter("y").length() > 8
                ) {
                    throw new IllegalArgumentException("Invalid parameters");
                }
                double x = Double.parseDouble(request.getParameter("x"));
                double y = Double.parseDouble(request.getParameter("y"));
                double r = Double.parseDouble(request.getParameter("r"));

                Set<Double> allowedValues = new HashSet<>(Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0));

                if (!(x >= -3 && x <= 5 && y >= -5 && y <= 3 && allowedValues.contains(r))) {
                    throw new IllegalArgumentException("Invalid parameters");
                }
            } catch (NullPointerException | IllegalArgumentException e) {
                response.sendError(400, "Invalid parameters");
                return;
            }
            forwardPath = this.getServletContext().getContextPath() + "/area?x=" + request.getParameter("x")
                    + "&y=" + request.getParameter("y") + "&r=" + request.getParameter("r");
        }

        response.sendRedirect(forwardPath);
    }
}

