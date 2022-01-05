<%@ page import="com.aston.coursework.handler.LessonHandler" %>
<%@ page import="com.aston.coursework.model.Lesson" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="navigationBar.jsp"/>
    <title>View Lesson Timetable</title>
</head>
<body>
<iframe name="empty" id="empty" style="display: none;"></iframe>
<div class="container">
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Description</th>
            <th>Date</th>
            <th>Start time</th>
            <th>End time</th>
            <th>Level</th>
            <th>Choose Lesson</th>
        </tr>
        </thead>
        <tbody>
        <%
            LessonHandler lessonHandler = new LessonHandler();
            Map<String, Lesson> selection = lessonHandler.getClientLessonSelection(request).getChosenLessons();
            // check if lesson selection is at maximum
            boolean maxLessonChosen = selection.size() == 3;
            for (Lesson lesson : lessonHandler.getLessonTimeTable().values()) {
                out.println("<tr>");
                out.println("<td>" + lesson.getDescription() + "</td>");
                out.println("<td>" + lesson.getDate() + "</td>");
                out.println("<td>" + lesson.getStartTime() + "</td>");
                out.println("<td>" + lesson.getEndTime() + "</td>");
                out.println("<td>" + lesson.getLevel() + "</td>");
                out.println("<td>");
                String formId = "lesson_form" + lesson.getId();
                String buttonState = "";
                // disable button if lesson selection is at maximum or lesson in selection model.
                if (maxLessonChosen || selection.get(lesson.getId()) != null) {
                    buttonState = "disabled";
                }
                out.println("<form method=\"post\" id=\"" + formId + "\" action=\"../do/chooseLesson\"></form>");
                out.println("<input type=\"hidden\" name=\"lessonId\" value=\"" + lesson.getId() + "\" form=\"" + formId + "\" />");
                out.println("<button type=\"submit\" form=\"" + formId + "\" " + buttonState + ">Select Lesson</button>");
                out.println("</td>");
                out.println("</tr>");
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
