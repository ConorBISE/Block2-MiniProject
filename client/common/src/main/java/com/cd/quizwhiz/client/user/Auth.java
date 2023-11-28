package com.cd.quizwhiz.client.user;

import org.json.JSONObject;

import com.cd.quizwhiz.client.net.NetClient;

public class Auth {
    @FunctionalInterface
    public interface RegisterCallback {
        public void run(String message, String token);
    }

    @FunctionalInterface
    public interface LoginCallback {
        public void run(boolean success, String token);
    }

    public static void register(NetClient netClient, String username, String password, RegisterCallback callback) {
        JSONObject body = new JSONObject();
        body.put("username", username);
        body.put("password", password);
        
        netClient.postRequest("/register", body, (res) -> {
            callback.run((String) res.get("message"), (String) res.get("token"));
        });
    }

    public static void login(NetClient netClient, String username, String password, LoginCallback callback) {
        JSONObject body = new JSONObject();
        
        body.put("username", username);
        body.put("password", password);
        
        netClient.postRequest("/login", body, (res) -> {
            // No news is good news - if we don't have a message, everything went okay!
            callback.run(res.get("message").equals(JSONObject.NULL), (String) res.get("token"));
        });   
    }
}
