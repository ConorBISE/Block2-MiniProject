package com.cd.quizwhiz.client.uiframework;

import java.util.function.Consumer;

/**
 * The template all UI pages within the framework must follow.
 */
public abstract class UIPage<T> {
    private final String pageName;

    protected UIPage(String pageName) {
        this.pageName = pageName;
    }

    /**
     * Called right before a page is loaded.
     * Useful for setting context required for the UI render.
     * 
     * @param ui a reference to the UI
     * @param callback a callback function to continue execution with. Useful for making network requests within 
     * @return Whether or not to finish the page load. Useful in the case onLoad is
     *         called within onPreload.
     */
    public void onPreload(UI<T> ui, Consumer<Boolean> callback) {
        callback.accept(true);
    }

    /**
     * Called right after a page is loaded.
     * Useful for dynamic DOM manipulation.
     * 
     * @param ui
     */
    public void onStart(UI<T> ui) {
    }

    public String getPageName() {
        return pageName;
    }
}
