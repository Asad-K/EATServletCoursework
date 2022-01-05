<%@ page import="com.aston.coursework.handler.LessonHandler" %>
<%@ page import="com.aston.coursework.model.Lesson" %>
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
            <th>Cancel Lesson</th>
        </tr>
        </thead>
        <tbody>
        <%
            LessonHandler lessonHandler = new LessonHandler();
            for (Lesson lesson : lessonHandler.getClientLessonSelection(request).getChosenLessons().values()) {
                out.println("<tr>");
                out.println("<td>" + lesson.getDescription() + "</td>");
                out.println("<td>" + lesson.getDate() + "</td>");
                out.println("<td>" + lesson.getStartTime() + "</td>");
                out.println("<td>" + lesson.getEndTime() + "</td>");
                out.println("<td>" + lesson.getLevel() + "</td>");
                out.println("<td>");
                String formId = "lesson_form" + lesson.getId();
                out.println("<form method=\"post\" id=\"" + formId + "\" action=\"../do/removeChosenLesson\"></form>");
                out.println("<input type=\"hidden\" name=\"lessonId\" value=\"" + lesson.getId() + "\" form=\"" + formId + "\" />");
                out.println("<button type=\"submit\" form=\"" + formId + "\">Cancel</button>");
                out.println("</td>");
                out.println("</tr>");
            }
        %>
        </tbody>
    </table>
    <form method="post" id="form" action="../do/finaliseBooking" target="empty"></form>
    <button type="submit" form="form">Finalise</button>
</div>

</body>
</html>
