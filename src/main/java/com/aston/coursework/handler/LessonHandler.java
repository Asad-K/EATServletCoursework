package com.aston.coursework.handler;

import com.aston.coursework.model.Lesson;
import com.aston.coursework.model.LessonSelection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.*;

public class LessonHandler {

    private Map<String, Lesson> lessonTimeTable = new HashMap<>();

    public LessonHandler() {
        for (Lesson lesson : Lesson.fromDatabase()) {
            lessonTimeTable.put(lesson.getId(), lesson);
        }
    }

    public Map<String, Lesson> getLessonTimeTable() {
        return lessonTimeTable;
    }

    public boolean setSessionAttributes(HttpSession session) {
        int clientId = (int) session.getAttribute("clientId");
        try {
            // set client lesson selection in session scope
            session.setAttribute("lessonSelection", new LessonSelection(clientId));
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void bookClient(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        LessonSelection selection = getClientLessonSelection(request);
        selection.updateBooking();
    }


    public void addLessonToCurrentSelection(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        // example attributes: {"lessonId": "L1"}
        LessonSelection selection = getClientLessonSelection(request);
        Lesson lessonToAdd = lessonTimeTable.get(request.getParameter("lessonId"));
        if (lessonToAdd != null && selection.getChosenLessons().size() < 3) {
            selection.addLesson(lessonToAdd);
        }
    }

    public void removeLessonFromCurrentSelection(HttpServletRequest request) {
        // example attributes: {"lessonId": "L1"}
        LessonSelection selection = getClientLessonSelection(request);
        Lesson lessonToRemove = lessonTimeTable.get(request.getParameter("lessonId"));
        if (lessonToRemove != null) {
            selection.removeLesson(lessonToRemove);
        }
    }

    public LessonSelection getClientLessonSelection(HttpServletRequest request) {
        return (LessonSelection) request.getSession(false).getAttribute("lessonSelection");
    }
}
