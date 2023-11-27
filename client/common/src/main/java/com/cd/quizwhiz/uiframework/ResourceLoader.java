package com.cd.quizwhiz.uiframework;

public abstract class ResourceLoader {
    @FunctionalInterface
    public interface ResourceContentCallback {
        void callback(String content);
    }

    public abstract String getResourceExternalForm(String resourcePath);
    public abstract void getResourceContent(String resourcePath, ResourceContentCallback callback);
}
