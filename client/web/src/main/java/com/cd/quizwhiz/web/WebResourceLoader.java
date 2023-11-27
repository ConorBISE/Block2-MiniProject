package com.cd.quizwhiz.web;

import org.teavm.jso.ajax.XMLHttpRequest;

import com.cd.quizwhiz.uiframework.ResourceLoader;

public class WebResourceLoader extends ResourceLoader {

    @Override
    public String getResourceExternalForm(String resourcePath) {
        return "/res" + resourcePath;
    }

    @Override
    public void getResourceContent(String resourcePath, ResourceContentCallback callback) {
        XMLHttpRequest xhr = XMLHttpRequest.create();
        xhr.open("GET", this.getResourceExternalForm(resourcePath));

        xhr.onComplete(() -> {
            callback.callback(xhr.getResponseText());
        });

        xhr.send();
    }
    
}
