package com.aston.coursework.handler;

import com.aston.coursework.model.Users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class LoginHandler {
    private Users users = new Users();

    public boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // check if session exists and has a clientId
            return session.getAttribute("clientId") != null;
        }
        return false;
    }

    public HttpSession login(HttpServletRequest request) {
        int clientId = users.isValid(request.getParameter("username"), request.getParameter("password"));
        if (clientId != -1) {
            // create user session, set user and clientId attributes.
            HttpSession session = request.getSession(true);
            session.setAttribute("user", request.getParameter("username"));
            session.setAttribute("clientId", clientId);
            return session;
        }
        return null;
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

}
