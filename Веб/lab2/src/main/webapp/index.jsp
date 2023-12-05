<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.demowildfly.model.UserAreaData" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="results" class="com.example.demowildfly.model.UserAreaData" scope="session" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Kachanov Daniil Vladimirovich P3206 WEB lab2">
    <meta name="author" content="Kachanov Daniil">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="styles/styles.css">
    <script src="https://cdn.jsdelivr.net/npm/superagent"></script>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>

<body>
<header>
    <strong> Kachanov Daniil P3206 Variant:1740</strong>
    ${pageContext.request.contextPath}/controller



</header>
<div class="main-container">

    <div class="params-select">
        <canvas id="canvas" width="300" height="300"></canvas>
        <form action="${pageContext.request.contextPath}/controller" method="post"><!--target="hidden_iframe">-->
            <input type="hidden" id="Xinput" name="hidden_x" value="0">
            <div class="selectionX">
                Choose value of X:
                <select id="Xselection" name="x">
                    <option value="-3">-3</option>
                    <option value="-2">-2</option>
                    <option value="-1">-1</option>
                    <option value="0" selected>0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>

                <div class="error-message" id="errorX">Invalid X: Please choose a valid value.</div>

            </div>
            <div class="inputY">
                <p style="display: inline;">Enter value of Y:</p>
                <input type="text" id="Yinput" name="y">
                <div class="error-message" id="errorY">Invalid Y: Please choose a valid value (-5, 3) with length less 15 after point.</div>

            </div>
            <div class="inputR">
                <p style="display: inline;">Enter value of R:</p>
                <select id="Rinput" name="r">
                    <option value="1">1</option>
                    <option value="1.5">1.5</option>
                    <option value="2">2</option>
                    <option value="2.5" selected>2.5</option>
                    <option value="3">3</option>
                </select>
                <div class="error-message" id="errorR">Invalid R: Please enter a numeric integer value (1,3) with length less 15 after point.</div>

            </div>
            <div class="btn-block">
                <button type="submit" id="submit" class="control-btn btn-process" disabled>Process</button>
            </div>
        </form>
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input id="clear" type="hidden" name="clear" value="1" />
            <input type="submit" class="control-btn btn-process" value="Clear table">
        </form>
        <iframe name="hiddenFrame" style="display:none;"></iframe>
    </div>
</div>


    <table id="result-table">
        <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Result</th>
            <th>Current Time</th>
            <th>Execution time</th>
        </tr>
        <c:forEach var="areaData" items="${results.data}">
            <tr>
                <td>${areaData.x}</td>
                <td>${areaData.y}</td>
                <td>${areaData.r}</td>
                <td>${areaData.resultString}</td>
                <td>${areaData.currentTime}</td>
                <td>${areaData.calculationTime}</td>
            </tr>
        </c:forEach>

    </table>

<script type="module" src="scripts/script_buttons.js"></script>
<script type="module" src="scripts/script_validation.js"></script>
<script type="module" src="scripts/canvas.js"></script>
<script src="scripts/point.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        <c:forEach var="areaData" items="${results.data}">
        addPoint(${areaData.r}, ${areaData.x}, ${areaData.y});
        </c:forEach>
    });
</script>

</body>

</html>
