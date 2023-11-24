package com.cd.quizwhiz.desktop;

import com.cd.quizwhiz.ui.AppState;
import com.cd.quizwhiz.ui.HomePage;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        new JavaFXUI<AppState>(new AppState(), primaryStage)
                .loadPage(new HomePage());
    }
}