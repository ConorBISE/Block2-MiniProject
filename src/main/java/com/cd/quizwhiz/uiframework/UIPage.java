package com.cd.quizwhiz.uiframework;

public class UIPage<T> {
    private String pageName;

    protected UIPage(String pageName) {
        this.pageName = pageName;
    };

    public boolean onPreload(UI<T> ui) { return true; };
    public void onStart(UI<T> ui) {};

    public String getPageName() { return pageName; }
}
