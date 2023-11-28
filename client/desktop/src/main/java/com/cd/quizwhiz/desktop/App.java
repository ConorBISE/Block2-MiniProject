package com.cd.quizwhiz.desktop;

import com.cd.quizwhiz.client.ui.AppState;
import com.cd.quizwhiz.client.ui.HomePage;
import com.cd.quizwhiz.desktop.net.DesktopNetClient;
import com.cd.quizwhiz.desktop.ui.DesktopResourceLoader;
import com.cd.quizwhiz.desktop.ui.JavaFXUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        new JavaFXUI<AppState>(new AppState(), new DesktopResourceLoader(), new DesktopNetClient(), primaryStage)
                .loadPage(new HomePage());
    }
}