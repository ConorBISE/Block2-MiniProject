package com.cd.quizwhiz.desktop.net;

import java.util.function.Consumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.cd.quizwhiz.client.net.NetClient;

public class DesktopNetClient extends NetClient {

    @Override
    public void getRequest(String path, String token, Consumer<JSONObject> callback) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(NetClient.BASE_URL + path);

            if (token != null)
                get.setHeader("Authorization", "Bearer " + token); 
            
            HttpResponse res = httpClient.execute(get);
            
            String bodyString = new BasicResponseHandler().handleResponse(res);
            callback.accept(new JSONObject(bodyString));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postRequest(String path, String token, JSONObject body, Consumer<JSONObject> callback) {  
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(NetClient.BASE_URL + path);
            post.setHeader("Content-type", "application/json");
            post.setEntity(new StringEntity(body.toString()));

            if (token != null)
                post.setHeader("Authorization", "Bearer " + token); 

            HttpResponse res = httpClient.execute(post);
            
            String bodyString = new BasicResponseHandler().handleResponse(res);
            callback.accept(new JSONObject(bodyString));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
