package com.cd.quizwhiz.web.ui;

import java.util.function.Consumer;

import org.json.JSONObject;

import com.cd.quizwhiz.client.net.NetClient;

public class WebNetClient extends NetClient {

    @Override
    public void getRequest(String path, String token, Consumer<JSONObject> callback) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRequest'");
    }

    @Override
    public void postRequest(String path, String token, JSONObject body, Consumer<JSONObject> callback) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'postRequest'");
    }
    
}
