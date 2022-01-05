package com.aston.coursework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aston.coursework.handler.LessonHandler;
import com.aston.coursework.handler.LoginHandler;


@WebServlet(name = "Controller", value = "/do/*")
public class Controller extends HttpServlet {

    private LessonHandler lessonHandler;
    private LoginHandler loginHandler;

    public void init() {
        lessonHandler = new LessonHandler();
        loginHandler = new LoginHandler();
    }

    public void destroy() {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println(request.getPathInfo());
        if (loginHandler.isLoggedIn(request)) {
            PrintWriter pw = response.getWriter();
            try {
                switch (request.getPathInfo()) {
                    case "/removeChosenLesson":
                        lessonHandler.removeLessonFromCurrentSelection(request);
                        // refresh page
                        response.sendRedirect("../do/viewSelection");
                        break;
                    case "/chooseLesson":
                        lessonHandler.addLessonToCurrentSelection(request);
                        // refresh page
                        response.sendRedirect("../do/viewTimetable");
                        break;
                    case "/finaliseBooking":
                        lessonHandler.bookClient(request);
                        break;
                }
            } catch (SQLException | ClassNotFoundException e) {
                // set error message then forward error jsp
                request.setAttribute("errorMessage", "Unknown Error Occurred");
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            }
            pw.close();
        } else {
            response.sendRedirect("../auth/login");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getPathInfo());
        if (loginHandler.isLoggedIn(request)) {
            switch (request.getPathInfo()) {
                case "/viewTimetable":
                    // send timetableView.jsp
                    request.getRequestDispatcher("/WEB-INF/viewTimetable.jsp").forward(request, response);
                    break;
                case "/viewSelection":
                    // send viewSelection.jsp
                    request.getRequestDispatcher("/WEB-INF/viewSelection.jsp").forward(request, response);
                    break;
            }
        } else {
            response.sendRedirect("../auth/login");
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Manages application endpoints";
    }
}
