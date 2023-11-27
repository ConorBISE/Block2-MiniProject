package com.cd.quizwhiz.web;

import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.w3c.dom.events.EventListener;

import com.cd.quizwhiz.client.uiframework.ResourceLoader;
import com.cd.quizwhiz.client.uiframework.UI;
import com.cd.quizwhiz.client.uiframework.UIPage;

public class WebUI<T> extends UI<T> {
    private OnPageLoadCallback pageLoadCallback;

    public WebUI(T initialState, ResourceLoader resourceLoader) {
        super(initialState, resourceLoader);
    }

    @Override
    protected void loadPageContent(String html) {
        HTMLDocument.current().getDocumentElement().setInnerHTML(html);
        this.pageLoadCallback.onPageLoad();
    }

    @Override
    protected void onPageLoad(UIPage<T> page, OnPageLoadCallback pageLoadCallback) {
        this.pageLoadCallback = pageLoadCallback;
    }

    @Override
    public void addListener(String id, String eventType, EventListener listener) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addListener'");
    }

    @Override
    public String getInputValueById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInputValueById'");
    }

    @Override
    public void setElementText(String id, String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setElementText'");
    }

    @Override
    public void setElementVisibility(String id, boolean visible) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setElementVisibility'");
    }

    @Override
    public void setElementClasses(String id, String classes) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setElementClasses'");
    }

    @Override
    public void setTitle(String title) {
        HTMLDocument.current().setTitle(title);
    }

    @Override
    public void setIcon(String iconPath) {
        HTMLDocument document = HTMLDocument.current();
        HTMLElement link = document.querySelector("link[rel~='icon']");
        if (link == null) {
            link = document.createElement("link");
            link.setAttribute("rel", "icon");
            document.getHead().appendChild(link);
        }

        link.setAttribute("href", this.getResourceLoader().getResourceExternalForm(iconPath));
    }

}
