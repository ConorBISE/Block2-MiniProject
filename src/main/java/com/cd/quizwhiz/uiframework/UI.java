package com.cd.quizwhiz.uiframework;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLInputElement;
import org.w3c.dom.html.HTMLSelectElement;

import com.cd.quizwhiz.App;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class UI<T> {
    private Stage primaryStage;
    private WebView webView;
    private T state;

    private ChangeListener<? super State> activeChangeListener;

    public UI(Stage primaryStage, T initialState) {
        this.state = initialState;
        this.primaryStage = primaryStage;

        this.webView = new WebView();

        StackPane pane = new StackPane();
        pane.getChildren().add(webView);

        Scene scene = new Scene(pane);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void loadPage(UIPage<T> page) {
        boolean shouldLoad = page.onPreload(this);

        if (!shouldLoad) {
            return;
        }

        WebEngine engine = webView.getEngine();
        Worker<?> worker = engine.getLoadWorker();

        engine.load(App.class.getResource("/ui/" + page.getPageName() + ".html").toExternalForm());

        if (this.activeChangeListener != null)
            worker.stateProperty().removeListener(this.activeChangeListener);

        this.activeChangeListener = (o, old, workerState) -> {
            // Nothing more should happen until the page is fully loaded
            if (workerState != Worker.State.SUCCEEDED) {
                return;
            }

            // Bind any event listeners that should be bound
            Class<?> pageClass = page.getClass();

            for (Method method : pageClass.getDeclaredMethods()) {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation instanceof ClickListener) {
                        ClickListener l = (ClickListener) annotation;
                        this.addClickListener(l.id(), event -> {
                            try {
                                method.invoke(page, this);
                            } catch (IllegalAccessException e) {
                                System.out.println("[WARN] Failed to invoke event listener for element " + l.id() + ": " + e);
                            }
                            catch (InvocationTargetException e) {
                                System.out.println("[WARN] Failure while invoking event listener for element " + l.id() + ": " + e.getTargetException());
                            }
                        });
                    }
                }
            }

            page.onStart(this);
        };

        worker.stateProperty().addListener(this.activeChangeListener);
    }

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

    public void setElementText(String id, String text) {
        Document document = webView.getEngine().getDocument();
        Element el = document.getElementById(id);
        el.setTextContent(text);
    }

    public void setElementVisibility(String id, boolean visible) {
        Document document = webView.getEngine().getDocument();
        Element el = document.getElementById(id);
        el.setAttribute("style", visible ? "display: block;" : "display: none;");
    }

    public void setElementClasses(String id, String classes) {
        Element el = webView.getEngine().getDocument().getElementById(id);
        el.setAttribute("class", classes);
    }

    public void addClickListener(String id, EventListener listener) {
        EventTarget eventTarget = (EventTarget) webView.getEngine().getDocument().getElementById(id);
        eventTarget.addEventListener("click", listener, true);
    }

    public T getState() {
        return state;
    };
    
    public void setTitle(String title) {
        this.primaryStage.setTitle(title);
    }
}
