package com.cd.quizwhiz.client.stats;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

import org.json.JSONArray;

import com.cd.quizwhiz.client.net.NetClient;

/**
 * The `Leaderboard` class provides methods for retrieving and managing user
 * leaderboards,
 * including sorting users by their top scores and including the current user's
 * score.
 */
public class Leaderboard {
    /**
     * Returns a 2D array of usernames and their top scores from user data files in
     * a directory.
     *
     * @return A 2D array (String[][]) where each row contains a username and their
     *         top score in descending order of score.
     * @throws IOException If an I/O error occurs when opening or reading a file.
     */
    public static void getLeaderboard(NetClient netClient, Consumer<String[][]> callback) {
        netClient.getRequest("/leaderboard", (res) -> {
            JSONArray leaderboard = res.getJSONArray("leaderboard");

            List<List<String>> l = new ArrayList<>();

            for (Object o : leaderboard) {
                JSONArray inner = (JSONArray) o;
                List<String> ll = new ArrayList<>();

                for (Object p : inner) {
                    ll.add((String) p);
                }

                l.add(ll);
            }

            callback.accept(l.stream()
                    .map(a -> a.stream().toArray(String[]::new))
                    .toArray(String[][]::new));
        });
    }

}
