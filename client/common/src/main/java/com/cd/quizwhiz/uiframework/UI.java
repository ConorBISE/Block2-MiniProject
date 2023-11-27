package com.cd.quizwhiz.uiframework;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.w3c.dom.events.EventListener;

import com.floreysoft.jmte.Engine;

/**
 * A very simple MVC app framework built around a generic HTML-rendering component.
 * Allows for HTML views to be backed by Java models and controllers
 * near-transparently.
 */
public abstract class UI<T> {
    private final T state;

    private Engine engine;
    private HashMap<String, Object> currentPageContext;

    @FunctionalInterface
    public interface OnPageLoadCallback {
        void onPageLoad();
    }

    public UI(T initialState) {
        this.state = initialState;

        this.engine = new Engine();
    }

    public void loadPage(UIPage<T> page) {
        // Every time we load a new page, reset our context.
        // (the set of variables the page template has access to)
        currentPageContext = new HashMap<>();

        // Because we're routing all our pages through thymeleaf,
        // our renderer won't assign it a concrete URL. This messes relative resource
        // loading up.
        // To fix this: let the template know where on disk it is!
        // Putting the value of base inside a <base> element will let our renderer know where
        // to
        // load resources from.
        String pageURL = "/" + page.getPageName() + ".html";
        currentPageContext.put("base", UI.class.getResource(pageURL).toExternalForm());

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

        this.onPageLoad(page, () -> {
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
                                System.err.println("Failed to invoke event listener for element " + l.id());
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                System.err.println("Failure while invoking event listener for element " + l.id());
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }

            // Once all our ducks are in a row - hand off to the page class to let
            // it do its post-load setup
            page.onStart(this);
        });

        // Finally: render out the page template, and have our WebView show it!
        String template = new BufferedReader(new InputStreamReader(UI.class.getResourceAsStream(pageURL)))
            .lines()
            .collect(Collectors.joining("\n"));
        
            String html = engine.transform(template, currentPageContext);
            
        this.loadPageContent(html);
    }

    protected abstract void loadPageContent(String html);
    protected abstract void onPageLoad(UIPage<T> page, OnPageLoadCallback pageLoadCallback);
    public abstract void addListener(String id, String eventType, EventListener listener);

    public abstract String getInputValueById(String id);
    public abstract void setElementText(String id, String text);
    public abstract void setElementVisibility(String id, boolean visible);
    public abstract void setElementClasses(String id, String classes);

    public abstract void setTitle(String title);
    public abstract void setIcon(String iconPath);

    public T getState() {
        return state;
    }

    public Map<String, Object> getContext() {
        return currentPageContext;
    }
}
