<%@ page import="com.aston.coursework.handler.LoginHandler" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    LoginHandler loginHandler = new LoginHandler();
    boolean isLoggedIn = loginHandler.isLoggedIn(request);
    if(isLoggedIn){
        response.sendRedirect("do/viewTimetable");
    } else {
        response.sendRedirect("auth/login");
    }
%>
