package com.aston.coursework.model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


import com.aston.coursework.util.DBUtil;


public class LessonTimetable {

    private Map<String, Lesson> lessons = new HashMap<>();

    public LessonTimetable() {
        try {
            Connection connection = DBUtil.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement("SELECT description, startDateTime, endDateTime, level, lessonid FROM lessons");
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    lessons.put(result.getString(5), new Lesson(result.getString(1),
                            result.getTimestamp(2),
                            result.getTimestamp(3),
                            result.getInt(4),
                            result.getString(5)));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Lesson getLesson(String itemID) {
        return this.lessons.get(itemID);
    }

    public Map<String, Lesson> getLessons() {
        return this.lessons;

    }

}
