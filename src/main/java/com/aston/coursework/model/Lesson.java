package com.aston.coursework.model;

import com.aston.coursework.util.DBUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// There is no no-arguments constructor, so at the moment this class can't be instantiated directly from JSPX using 'useBean'
public class Lesson {

    protected String description;
    protected String startTime;
    protected String date;
    protected String endTime;
    protected int level;
    protected String ID;

    // Factory method retrieves lessons from database
    public static List<Lesson> fromDatabase() {
        List<Lesson> lessons = new ArrayList<>();

        try {
            Connection connection = DBUtil.getConnection();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement("SELECT description, startDateTime, endDateTime, level, lessonid FROM lessons");
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    lessons.add(new Lesson(result.getString(1),
                            result.getTimestamp(2),
                            result.getTimestamp(3),
                            result.getInt(4),
                            result.getString(5)));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public Lesson(String description, Timestamp startDateTime, Timestamp endDateTime, int level, String id) {

        this.description = description;
        this.level = level;
        this.ID = id;

        // Use the Calendar class to convert between a Date object and formatted strings.
        Calendar c = Calendar.getInstance();
        c.setTime(startDateTime);
        // Get the details from this Date object
        SimpleDateFormat dateformatter = new SimpleDateFormat("E, dd MMM, yyyy");
        this.date = dateformatter.format(c.getTime());

        dateformatter = new SimpleDateFormat("kk:mm");
        this.startTime = dateformatter.format(c.getTime());

        // Extract the details from the 'endDateTime' Date object
        c.setTime(endDateTime);
        dateformatter = new SimpleDateFormat("kk:mm");
        this.endTime = dateformatter.format(c.getTime());
    }

    // We can make a copy of a lesson object, for example to store in a session.
    // This is done by populating the parameters of the new lesson using the accessor (getter) methods of the original.
    public Lesson(Lesson l) {

        this.description = l.getDescription();
        this.ID = l.getId();
        this.level = l.level;
        this.date = l.getDate();
        this.startTime = l.getStartTime();
        this.endTime = l.getEndTime();
    }

    /* Getter methods for all the properties of the object.
     * At the moment, this class has no setters.
     */
    public String getId() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return this.date;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public int getLevel() {
        return this.level;
    }

    public String toString() {
        return getId() + ", " +
                getDescription() + ", " +
                getStartTime() + ", " +
                getEndTime() + ", " +
                getLevel();
    }

}
