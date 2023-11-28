package com.cd.quizwhiz.web.ui;

import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLInputElement;
import org.teavm.jso.dom.html.HTMLSelectElement;
import org.w3c.dom.events.EventListener;

import com.cd.quizwhiz.client.net.NetClient;
import com.cd.quizwhiz.client.uiframework.ResourceLoader;
import com.cd.quizwhiz.client.uiframework.UI;
import com.cd.quizwhiz.client.uiframework.UIPage;

public class WebUI<T> extends UI<T> {
    private Runnable pageLoadCallback;

    public WebUI(T initialState, ResourceLoader resourceLoader, NetClient netClient) {
        super(initialState, resourceLoader, netClient);
    }

    @Override
    protected void loadPageContent(String html) {
        HTMLDocument.current().getDocumentElement().setInnerHTML(html);
        this.pageLoadCallback.run();
    }

    @Override
    protected void onPageLoad(UIPage<T> page, Runnable pageLoadCallback) {
        this.pageLoadCallback = pageLoadCallback;
    }

    @Override
    public void addListener(String id, String eventType, EventListener listener) {
        HTMLDocument.current().getElementById(id).addEventListener(eventType, (e) -> {
            listener.handleEvent(null);
        });
    }

    @Override
    public String getInputValueById(String id) {
        HTMLElement el = HTMLDocument.current().getElementById(id);

        switch (el.getTagName().toLowerCase()) {
            case "select":
                HTMLSelectElement sel = (HTMLSelectElement) el;
                return sel.getValue();
            
            case "input":
                HTMLInputElement inp = (HTMLInputElement) el;
                return inp.getValue();

            default:
                return null;
        }
    }

    @Override
    public void setElementText(String id, String text) {
        HTMLDocument.current().getElementById(id).setInnerText(text);
    }

    @Override
    public void setElementVisibility(String id, boolean visible) {
        HTMLElement el = HTMLDocument.current().getElementById(id);
        el.setAttribute("style", visible ? "display: block;" : "display: none;");
    }

    @Override
    public void setElementClasses(String id, String classes) {
        HTMLElement el = HTMLDocument.current().getElementById(id);
        el.setAttribute("class", classes);
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
