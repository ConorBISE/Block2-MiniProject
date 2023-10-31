package com.cd.quizwhiz.uiframework;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLInputElement;
import org.w3c.dom.html.HTMLSelectElement;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * A very simple MVC app framework built around JavaFX's WebView component.
 * Allows for HTML views to be backed by Java models and controllers
 * near-transparently.
 */
public class UI<T> {
    private final Stage primaryStage;
    private final WebView webView;

    // In order to be able to remove old change listeners registered from loading
    // old pages
    // we need to keep a reference to change listener currently active
    private ChangeListener<? super State> activeChangeListener;

    private final T state;

    private final TemplateEngine templateEngine;
    private Context currentPageContext;

    private final Logger logger = LoggerFactory.getLogger(UI.class);

    public UI(Stage primaryStage, T initialState) {
        this.state = initialState;
        this.primaryStage = primaryStage;

        // Create and add a WebView to the JavaFX scene
        // This is the one JavaFX thing we'll do - every other piece of UI occurs within
        // the confined of this WebView
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

        // Set up thymeleaf, our templating engine
        // We want it to load from the classpath, which will allow it to locate our
        // template even
        // when it's bundled up inside a jar file.
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCacheable(false);

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
    }

    public void loadPage(UIPage<T> page) {
        // Every time we load a new page, reset our context.
        // (the set of variables the page template has access to)
        currentPageContext = new Context();

        // Because we're routing all our pages through thymeleaf,
        // WebKit doesn't assign it a concrete URL. This messes relative resource
        // loading up.
        // To fix this: let the template know where on disk it is!
        // Putting the value of base inside a <base> element will let WebKit know where
        // to
        // load resources from.
        currentPageContext.setVariable("base",
                UI.class.getResource("/" + page.getPageName() + ".html").toExternalForm());

        // Stage one of page loading: preloading.
        // Pages can get all the information they want to have available to the template
        // into the context,
        // and, optionally, halt the page load entirely.
        // This is useful if the page realizes it needs to make a loadPage right away
        // (i.e. a page that requires the user to be logged in redirecting to a login
        // page)
        boolean shouldLoad = page.onPreload(this);

        if (!shouldLoad) {
            return;
        }

        WebEngine engine = webView.getEngine();
        Worker<?> worker = engine.getLoadWorker();

        // Next: we want to prepare for the things we want to happen after the page
        // loads
        // but first: housekeeping. We probably registered a listener last page load.
        // Let's get rid of it.
        if (this.activeChangeListener != null)
            worker.stateProperty().removeListener(this.activeChangeListener);

        this.activeChangeListener = (o, old, workerState) -> {
            if (workerState != Worker.State.SUCCEEDED) {
                return;
            }

            // Everything past this point inside the listener will only be run
            // when the page load is finished

            // We need to tie any convienience event handler annotations
            // (things like @ClickListener)
            // to the elements they're meant to be handling events for.
            Class<?> pageClass = page.getClass();

            for (Method method : pageClass.getMethods()) {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation instanceof UIEventListener) {
                        UIEventListener l = (UIEventListener) annotation;

                        this.addListener(l.id(), l.type(), event -> {
                            try {
                                method.invoke(page, this);
                            } catch (IllegalAccessException e) {
                                logger.error("Failed to invoke event listener for element {}:", l.id(), e);
                            } catch (InvocationTargetException e) {
                                logger.error("Failure while invoking event listener for element {}:", l.id(),
                                        e.getTargetException());
                            }
                        });
                    }
                }
            }

            // Once all our ducks are in a row - hand off to the page class to let
            // it do its post-load setup
            page.onStart(this);
        };

        // Have our listener fire any time the page changes state
        // (loading, loaded, etc.)
        worker.stateProperty().addListener(this.activeChangeListener);

        // Finally: render out the page template, and have our WebView show it!
        String html = this.templateEngine.process(page.getPageName(), currentPageContext);
        engine.loadContent(html);
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

    public void addListener(String id, String eventType, EventListener listener) {
        EventTarget eventTarget = (EventTarget) webView.getEngine().getDocument().getElementById(id);
        eventTarget.addEventListener(eventType, listener, true);
    }

    public T getState() {
        return state;
    }

    public Context getContext() {
        return currentPageContext;
    }

    public void setTitle(String title) {
        this.primaryStage.setTitle(title);
    }

    public void setIcon(String iconPath) {
        this.primaryStage.getIcons().add(new Image(UI.class.getResourceAsStream(iconPath)));
    }
}
