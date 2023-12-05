package com.example.demowildfly;

import com.example.demowildfly.model.AreaData;
import com.example.demowildfly.model.UserAreaData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "area", value = "/area")
public class AreaCheckServlet extends HttpServlet {

    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        response.getWriter().println("Here should be a GET request!");
    }

    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        try{
            if(request.getParameter("clear") == "1"){
                UserAreaData attr = new UserAreaData();
                HttpSession session = request.getSession();
                session.setAttribute("results", attr);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        double x ;
        double y ;
        double r ;
        try {
             x = Double.parseDouble(request.getParameter("x"));
             y = Double.parseDouble(request.getParameter("y"));
             r = Double.parseDouble(request.getParameter("r"));

            Set<Double> allowedValues = new HashSet<>(Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0));

            if (!(x >= -3 && x <= 5 && y >= -5 && y <= 3 && allowedValues.contains(r))) {
                throw new IllegalArgumentException("Invalid parameters");
            }

            HttpSession session = request.getSession();

            long startTime = System.nanoTime();
            AreaData data = new AreaData(x, y, r, checkPointInArea(r, x, y));
            data.setCalculationTime(System.nanoTime() - startTime);
            UserAreaData attr = (UserAreaData) session.getAttribute("results");
            if (attr == null) {
                attr = new UserAreaData();
                session.setAttribute("results", attr);
            }
            attr.addData(data);
            session.setAttribute("results", attr);

            String resultPage = """
                  <!DOCTYPE html>
                                   <html lang="en">
                                   
                                   <head>
                                       <meta charset="UTF-8">
                                       <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                       <title>AreaCheckServlet</title>
                                       <link rel="stylesheet" type="text/css" href="styles/styles.css">
                                   </head>
                                   
                                   <body style="display: flex; flex-direction: column; align-items: center; justify-content: center;">
                                       <h1>AreaCheckServlet</h1>
                                       <p><strong>Point parameters:</strong></p>
                                       <table>
                                           <tr>
                                               <th>Parameter</th>
                                               <th>Value</th>
                                           </tr>
                                           <tr>
                                               <td>X</td>
                                               <td>%s</td>
                                           </tr>
                                           <tr>
                                               <td>Y</td>
                                               <td>%s</td>
                                           </tr>
                                           <tr>
                                               <td>R</td>
                                               <td>%s</td>
                                           </tr>
                                       </table>
                                       <p><strong>Check result:</strong></p>
                                       <ul>
                                           <li>Hit: %s</li>
                                       </ul>
                                       <p><strong>Calculation time:</strong> %d milliseconds</p>
                                       <p><strong>Check history:</strong></p>
                                       <table>
                                           <tr>
                                               <th>X</th>
                                               <th>Y</th>
                                               <th>R</th>
                                               <th>Result</th>
                                               <th>Current time</th>
                                               <th>Calculation Time</th>
                                           </tr>
                                           %s
                                       </table>
                                       <form action="%s/controller" method="post">
                                        <strong>Link to the web form page</strong>
                                       <input type="submit" value="Submit">
                                       </form>
                                   </body>
                                   
                                   </html>
                                   
                """;

            StringBuilder historyRows = new StringBuilder();
            for (AreaData historyData : attr.getData()) {
                historyRows.append("<tr>")
                        .append("<td>").append(historyData.getX()).append("</td>")
                        .append("<td>").append(historyData.getY()).append("</td>")
                        .append("<td>").append(historyData.getR()).append("</td>")
                        .append("<td>").append(historyData.getResultString()).append("</td>")
                        .append("<td>").append(historyData.getCurrentTime()).append("</td>")
                        .append("<td>").append(historyData.getCalculationTime()).append("</td>")
                        .append("</tr>");
            }

            response.getWriter().printf(resultPage, x, y, r, data.getResultString(), data.getCalculationTime(), historyRows.toString(), this.getServletContext().getContextPath());

        } catch (Exception e) {
            response.sendError(400, "Invalid parameters");
        }
 }

    boolean checkPointInArea(double R, double X, double Y) {
        boolean sectorI = (X >= 0) && (Y >= 0) && (X + Y <= R/2);
        boolean sectorIV = (X <= 0) && (Y >= 0) && (X * X + Y * Y <= R*R/4);
        boolean sectorIII = (X <= 0) && (Y <= 0) && (X >= -R) & (Y >= -R);
        return sectorI || sectorIV || sectorIII;
    }
}
