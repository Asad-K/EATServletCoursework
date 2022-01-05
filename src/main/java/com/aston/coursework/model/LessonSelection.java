package com.aston.coursework.model;

import java.sql.*;
import java.util.HashMap;

import com.aston.coursework.util.DBUtil;


public class LessonSelection {

    private HashMap<String, Lesson> chosenLessons;
    private int ownerID;

    /**
     * TODO get the details of any lessons currently selected by this user.
     * One way to do this is create a join query which:
     *      1. finds rows in the 'lessons_booked' table which relate to this clientid
     *      2. links 'lessons' to 'lessons_booked' by 'lessonid
     *      3. selects all fields from lessons for these rows
     *      If you need to test your SQL syntax you can do this in any of your MySql clients.
     *      For each one, instantiate a new Lesson object,
     *      and add it to this collection (use 'LessonSelection.addLesson()' )
     */
    public LessonSelection(int owner) throws SQLException, ClassNotFoundException {
        chosenLessons = new HashMap<>();
        ownerID = owner;
        Connection connection = DBUtil.getConnection();
        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement("SELECT  lessons.description, lessons.startDateTime, lessons.endDateTime, lessons.level, lessons.lessonid FROM lessons RIGHT JOIN lessons_booked lb on lessons.lessonid = lb.lessonid WHERE clientid = ?");
            statement.setInt(1, ownerID);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                addLesson(new Lesson(
                        result.getString(1),
                        result.getTimestamp(2),
                        result.getTimestamp(3),
                        result.getInt(4),
                        result.getString(5)));
            }
            connection.close();
        }
    }


    // TODO In the database, delete any existing lessons booked for this user in the table 'lessons_booked'
    public void clearBookings() throws SQLException, ClassNotFoundException {
        Connection connection = DBUtil.getConnection();
        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM lessons_booked WHERE clientid = ?");
            statement.setInt(1, ownerID);
            statement.executeUpdate();
        }
    }

    // TODO - write and execute a query which, for each selected lesson, will insert into the correct table:
    public void updateBooking() throws SQLException, ClassNotFoundException {
        clearBookings();
        Connection connection = DBUtil.getConnection();
        if (connection != null) {
            for (String lessonId : chosenLessons.keySet()) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO lessons_booked (clientid, lessonid) VALUES (?, ?)");
                statement.setInt(1, ownerID);
                statement.setString(2, lessonId);
                statement.executeUpdate();
            }

        }
    }

    public void addLesson(Lesson lesson) {
        // if lesson isn't already chosen
        if (chosenLessons.get(lesson.getId()) == null) {
            chosenLessons.put(lesson.getId(), new Lesson(lesson));
        }
    }

    public void removeLesson(Lesson lesson) {
        chosenLessons.remove(lesson.getId());
    }

    public Lesson getLesson(String id) {
        return this.chosenLessons.get(id);
    }

    public HashMap<String, Lesson> getChosenLessons() {
        return chosenLessons;
    }

    public int getOwner() {
        return this.ownerID;
    }

}
