package com.cd.quizwhiz;

import com.cd.quizwhiz.ui.UI;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        new UI().run(primaryStage);
    }
}