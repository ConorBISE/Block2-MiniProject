package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.App;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class UI {
    private WebView webView;

    private void loadPage(String pageName) { 
        webView.getEngine().load(App.class.getResource("/ui/" + pageName + ".html").toExternalForm());
    }

    /**
     * Creates & shows the very first screen of the app
     * @param primaryStage  the JavaFX stage our WebView will be created on
     */
    public void run(Stage primaryStage) {
        primaryStage.setTitle("quizwhiz");

        webView = new WebView();
        loadPage("index");

        StackPane pane = new StackPane();
        pane.getChildren().add(webView);
        
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
