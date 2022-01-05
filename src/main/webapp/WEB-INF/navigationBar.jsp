<%@ page import="com.aston.coursework.handler.LoginHandler" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    LoginHandler loginHandler = new LoginHandler();
    boolean isLoggedIn = loginHandler.isLoggedIn(request);
%>
<!DOCTYPE html>
<html lang="en">
<meta charset="utf-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <%
            if (isLoggedIn) {
                out.println("<div class=\"navbar-header\">");
                out.println("<a class=\"navbar-brand\"> Greetings " + session.getAttribute("user") + "</a>");
                out.println("</div>");
            }
        %>
        <ul class="nav navbar-nav">
            <li><a href="../do/viewTimetable">View Timetable</a></li>
            <li><a href="../do/viewSelection">View Selection</a></li>
            <%
                if (isLoggedIn) {
                    out.println("<li><a href=\"../auth/logout\">Logout</a></li>");
                } else {
                    out.println("<li><a href=\"../auth/login\">Login</a></li>");
                }
            %>

        </ul>
    </div>
</nav>
