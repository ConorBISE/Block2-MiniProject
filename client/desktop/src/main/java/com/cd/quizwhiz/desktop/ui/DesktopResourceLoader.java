package com.cd.quizwhiz.desktop.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.cd.quizwhiz.client.uiframework.ResourceLoader;

public class DesktopResourceLoader extends ResourceLoader {
    @Override
    public String getResourceExternalForm(String resourcePath) {
        return ResourceLoader.class.getResource(resourcePath).toExternalForm();
    }

    @Override
    public void getResourceContent(String resourcePath, Consumer<String> callback) {
        String content = new BufferedReader(new InputStreamReader(ResourceLoader.class.getResourceAsStream(resourcePath)))
            .lines()
            .collect(Collectors.joining("\n"));
        
        callback.accept(content);
    }
    
}
