module com.cd.quizwhiz {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.web;
    
    requires jdk.xml.dom;
    requires thymeleaf;
    requires java.sql;

    exports com.cd.quizwhiz;
    exports com.cd.quizwhiz.Questions;
    exports com.cd.quizwhiz.UserStuff;
    exports com.cd.quizwhiz.Stats;
}
