package com.cd.quizwhiz.desktop;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLInputElement;
import org.w3c.dom.html.HTMLSelectElement;

import com.cd.quizwhiz.client.uiframework.ResourceLoader;
import com.cd.quizwhiz.client.uiframework.UI;
import com.cd.quizwhiz.client.uiframework.UIPage;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class JavaFXUI<T> extends UI<T> {
    private final Stage primaryStage;
    private final WebView webView;

    // In order to be able to remove old change listeners registered from loading old pages
    // we need to keep a reference to change listener currently active
    private ChangeListener<? super State> activeChangeListener;

    public JavaFXUI(T initialState, ResourceLoader resourceLoader, Stage primaryStage) {
        super(initialState, resourceLoader);

        this.primaryStage = primaryStage;

        this.webView = new WebView();

        // By default, WebViews have a browser-style menu on right click, with options
        // to go back, or reload the page.
        // We don't want that.
        this.webView.setContextMenuEnabled(false);

        StackPane pane = new StackPane();
        pane.getChildren().add(webView);

        Scene scene = new Scene(pane);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    @Override
    protected void loadPageContent(String html) {
        this.webView.getEngine().loadContent(html);
    }

    @Override
    protected void onPageLoad(UIPage<T> page, OnPageLoadCallback pageLoadCallback) {
        WebEngine engine = webView.getEngine();
        Worker<?> worker = engine.getLoadWorker();

        // We probably registered a listener last page load.
        // Let's get rid of it.
        if (this.activeChangeListener != null)
            worker.stateProperty().removeListener(this.activeChangeListener);

        this.activeChangeListener = (o, old, workerState) -> {
            if (workerState != Worker.State.SUCCEEDED) {
                return;
            }

            pageLoadCallback.onPageLoad();
        };

        // Have our listener fire any time the page changes state
        // (loading, loaded, etc.)
        worker.stateProperty().addListener(this.activeChangeListener);
    }

    @Override
    public String getInputValueById(String id) {
        Document document = webView.getEngine().getDocument();
        Element el = document.getElementById(id);

        if (el instanceof HTMLSelectElement) {
            HTMLSelectElement selectEl = (HTMLSelectElement) el;
            return selectEl.getValue();
        }

        if (el instanceof HTMLInputElement) {
            HTMLInputElement inputEl = (HTMLInputElement) el;
            return inputEl.getValue();
        }

        return null;
    }

    @Override
    public void setElementText(String id, String text) {
        Document document = webView.getEngine().getDocument();
        Element el = document.getElementById(id);
        el.setTextContent(text);
    }

    @Override
    public void setElementVisibility(String id, boolean visible) {
        Document document = webView.getEngine().getDocument();
        Element el = document.getElementById(id);
        el.setAttribute("style", visible ? "display: block;" : "display: none;");
    }

    @Override
    public void setElementClasses(String id, String classes) {
        Element el = webView.getEngine().getDocument().getElementById(id);
        el.setAttribute("class", classes);
    }

    @Override
    public void addListener(String id, String eventType, EventListener listener) {
        EventTarget eventTarget = (EventTarget) webView.getEngine().getDocument().getElementById(id);
        eventTarget.addEventListener(eventType, listener, true);
    }

    @Override
    public void setTitle(String title) {
        this.primaryStage.setTitle(title);
    }

    @Override
    public void setIcon(String iconPath) {
        this.primaryStage.getIcons().add(new Image(UI.class.getResourceAsStream(iconPath)));
    }
}
