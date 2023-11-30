package com.cd.quizwhiz.client.uiframework;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.events.EventListener;

import com.cd.quizwhiz.client.net.NetClient;
import com.floreysoft.jmte.Engine;

/**
 * A very simple MVC app framework built around a generic HTML-rendering
 * component.
 * Allows for HTML views to be backed by Java models and controllers
 * near-transparently.
 */
public abstract class UI<T> {
    private final T state;
    private ResourceLoader resourceLoader;
    private NetClient netClient;

    private Engine engine;
    private HashMap<String, Object> currentPageContext;

    public UI(T initialState, ResourceLoader resourceLoader, NetClient netClient) {
        this.state = initialState;
        this.resourceLoader = resourceLoader;
        this.netClient = netClient;

        this.engine = new Engine();
    }

    public void loadPage(UIPage<T> page) {
        // Every time we load a new page, reset our context.
        // (the set of variables the page template has access to)
        currentPageContext = new HashMap<>();

        // Because we're routing all our pages through jmte,
        // our renderer won't assign it a concrete URL. This messes relative resource
        // loading up.
        // To fix this: let the template know where on disk it is!
        // Putting the value of base inside a <base> element will let our renderer know
        // where to load resources from.
        String templateURL = "/" + page.getPageName() + ".html";
        currentPageContext.put("base", this.resourceLoader.getResourceExternalForm(templateURL));

        // Stage one of page loading: preloading.
        // Pages can get all the information they want to have available to the template
        // into the context,
        // and, optionally, halt the page load entirely.
        // This is useful if the page realizes it needs to make a loadPage right away
        // (i.e. a page that requires the user to be logged in redirecting to a login
        // page)
        page.onPreload(this, (shouldLoad) -> {
            if (!shouldLoad) {
                return;
            }

            this.onPageLoad(page, () -> {
                // Once all our ducks are in a row - hand off to the page class to let
                // it do its post-load setup
                page.onStart(this);
            });

            // Finally: render out the page template, and have our WebView show it!
            this.resourceLoader.getResourceContent(templateURL, (content) -> {
                String html = engine.transform(content, currentPageContext);
                this.loadPageContent(html);
            });
        });
    }

    protected abstract void loadPageContent(String html);

    protected abstract void onPageLoad(UIPage<T> page, Runnable pageLoadCallback);

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

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public NetClient getNetClient() {
        return netClient;
    }
}
