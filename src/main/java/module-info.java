module com.cd.quizwhiz {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.web;
    
    requires jdk.xml.dom;
    requires org.json;

    exports com.cd.quizwhiz;
    exports com.cd.quizwhiz.Questions;
}
