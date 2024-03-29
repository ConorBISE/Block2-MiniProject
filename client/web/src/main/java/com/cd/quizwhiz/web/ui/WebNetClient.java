package com.cd.quizwhiz.web.ui;

import java.util.function.Consumer;

import org.json.JSONObject;
import org.teavm.jso.ajax.XMLHttpRequest;

import com.cd.quizwhiz.client.net.NetClient;

public class WebNetClient extends NetClient {

    @Override
    public void getRequest(String path, String token, Consumer<JSONObject> callback) {
        XMLHttpRequest xhr = XMLHttpRequest.create();
        xhr.open("GET", NetClient.BASE_URL + path);

        if (token != null)
            xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onComplete(() -> {
            callback.accept(new JSONObject(xhr.getResponseText()));
        });

        xhr.send();
    }

    @Override
    public void postRequest(String path, String token, JSONObject body, Consumer<JSONObject> callback) {
        XMLHttpRequest xhr = XMLHttpRequest.create();
        xhr.open("POST", NetClient.BASE_URL + path);

        if (token != null)
            xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onComplete(() -> {
            callback.accept(new JSONObject(xhr.getResponseText()));
        });

        xhr.send(body.toString());
    }
    
}
