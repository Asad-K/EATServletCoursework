package com.aston.coursework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.aston.coursework.handler.LessonHandler;
import com.aston.coursework.handler.LoginHandler;
import com.aston.coursework.model.Users;


@WebServlet(name = "Authentication", value = "/auth/*")
public class AuthenticationController extends HttpServlet {

    private Users users;
    private LoginHandler loginHandler;
    private LessonHandler lessonHandler;

    public void init() {
        users = new Users();
        loginHandler = new LoginHandler();
        lessonHandler = new LessonHandler();
    }

    public void destroy() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getPathInfo());
        switch (request.getPathInfo()) {
            case "/login":
                HttpSession session = loginHandler.login(request);
                if (loginHandler.isLoggedIn(request)) {
                    // set session attributes then redirect to view timetable
                    if (lessonHandler.setSessionAttributes(session)) {
                        // if session attributes were successfully set redirect ot viewTimetable
                        response.sendRedirect("../do/viewTimetable");
                        break;
                    }
                } else {
                    // set error message in request scope
                    request.setAttribute("errorMessage", "Unable to login.");
                    request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                }
                // if any errors occur redirect to login page
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                break;
            case "/addUser":
                // add user then redirect to login page
                String username = request.getParameter("newUsername");
                if (!users.addUser(username, request.getParameter("newPassword"))) {
                    // set error message in request scope
                    request.setAttribute("errorMessage", "Unable to new add user with the username: " + username);
                    request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                }
                response.sendRedirect("login");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getPathInfo());
        switch (request.getPathInfo()) {
            case "/login":
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                break;
            case "/logout":
                loginHandler.logout(request);
                response.sendRedirect("login");
                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Manages application authentication";
    }
}

