package com.cd.quizwhiz.web.ui;

import java.util.function.Consumer;

import org.teavm.jso.ajax.XMLHttpRequest;

import com.cd.quizwhiz.client.uiframework.ResourceLoader;

public class WebResourceLoader extends ResourceLoader {

    @Override
    public String getResourceExternalForm(String resourcePath) {
        return "/res" + resourcePath;
    }

    @Override
    public void getResourceContent(String resourcePath, Consumer<String> callback) {
        XMLHttpRequest xhr = XMLHttpRequest.create();
        xhr.open("GET", this.getResourceExternalForm(resourcePath));

        xhr.onComplete(() -> {
            callback.accept(xhr.getResponseText());
        });

        xhr.send();
    }
    
}
