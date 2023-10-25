package com.cd.quizwhiz.uiframework;

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
     * @param ui a reference to the UI
     * @return Whether or not to finish the page load. Useful in the case onLoad is called within onPreload.
     */
    public boolean onPreload(UI<T> ui) { return true; }

    /**
     * Called right after a page is loaded.
     * Useful for dynamic DOM manipulation.
     * @param ui
     */
    public void onStart(UI<T> ui) {}

    public String getPageName() { return pageName; }
}
