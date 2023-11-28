package com.cd.quizwhiz.client.uiframework;

import java.util.function.Consumer;

public abstract class ResourceLoader {
    public abstract String getResourceExternalForm(String resourcePath);
    public abstract void getResourceContent(String resourcePath, Consumer<String> callback);
}
