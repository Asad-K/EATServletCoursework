#Project structure
IntelliJ IDEA project.  
Gradle dependency management and build automation.  
Path to sources: src/main/java/com/aston/coursework  
Path to JSPs: src/main/webapp/WEB-INF

#Question answered
##Section A
a) /login -> com.aston.coursework.AuthenticationController:36  
b) /viewTimetable -> com.aston.controller.coursework.Controller:70  
c) /chooseLesson -> com.aston.controller.coursework.Controller:44  
d) /viewSelection -> com.aston.controller.coursework.Controller:74  
e) /finaliseBooking -> com.aston.controller.coursework.Controller:49  
f) /logOut -> com.aston.coursework.controller.AuthenticationController:75

##Section B
1: com.aston.coursework.handler.LessonHandler:48  
2: /addUser -> com.aston.coursework.controller.AuthenticationController:55

#Architecture
##Two controllers:  
com.aston.coursework.controller.AuthenticationController -> Controls authentication endpoints such as login, log out, add user.  
com.aston.coursework.controller.Controller -> Controls business logic endpoints.

##Two intermediary handlers
com.aston.coursework.handler.LessonHandler -> handles models related to lesson logic.  
com.aston.coursework.handler.LoginHandler -> handles login functionality.

#Database
please see: src/main/java/com/aston/coursework/util/DBUtil.java  

