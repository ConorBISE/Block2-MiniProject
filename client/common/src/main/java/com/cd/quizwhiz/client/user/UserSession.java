package com.cd.quizwhiz.client.user;

import java.util.function.Consumer;

import org.json.JSONObject;

import com.cd.quizwhiz.client.net.NetClient;

public class UserSession {
    private String username;
    private String token;
    private int currentScore;

    public UserSession(String username, String token) {
        this.username = username;
        this.token = token;
        this.currentScore = 0;
    }

    public void incrementScore()
    /**
     * Increases the user's current score by 1, typically used when a user gets an
     * answer correct.
     */
    {
        currentScore++;
    }

    public void saveScore(NetClient netClient, Runnable callback)
    /**
     * Gets the player's current and final score for the game, and saves it to the server
     */
    {
        JSONObject body = new JSONObject();
        body.put("score", currentScore);

        netClient.postRequest("/score/new", this.token, body, (res) -> {
            callback.run();
        });
    }

    public String getUsername() {
        return username;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void resetScore() {
        currentScore = 0;
    }

    public void getStats(NetClient netClient, Consumer<UserStats> callback) {
        netClient.getRequest("/stats", this.token, (res) -> {
            callback.accept(new UserStats(true, res.getDouble("mean"), res.getDouble("median"), res.getDouble("deviation")));
        });
    }

}
