<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="navigationBar.jsp"/>
    <title>Error Occurred</title>
</head>
<body>
<div class="container-fluid">
    <%
        out.println("<h3>An Error Occurred</h3>");
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage == null) {
            errorMessage = "An unknown error occurred";
        }
        out.println("<p>" + errorMessage + "</p>");
    %>
</div>
</body>
</html>
