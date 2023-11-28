package com.cd.quizwhiz.client.net;

import java.util.function.Consumer;

import org.json.JSONObject;

public abstract class NetClient {
    protected static String BASE_URL = "http://localhost:8080/";

    public void getRequest(String path, Consumer<JSONObject> callback) {
        getRequest(path, null, callback);
    }
    
    public abstract void getRequest(String path, String token, Consumer<JSONObject> callback);

    public void postRequest(String path, JSONObject body, Consumer<JSONObject> callback) {
        postRequest(path, null, body, callback);
    }

    public abstract void postRequest(String path, String token, JSONObject body, Consumer<JSONObject> callback);
}
