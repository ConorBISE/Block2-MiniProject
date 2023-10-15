package com.cd.quizwhiz;

import com.cd.quizwhiz.ui.AppState;
import com.cd.quizwhiz.ui.HomePage;
import com.cd.quizwhiz.uiframework.UI;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        new UI<AppState>(primaryStage, new AppState()).loadPage(new HomePage());
    }
}