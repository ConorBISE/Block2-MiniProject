package com.cd.quizwhiz.client.stats;

import java.io.*;
import java.nio.file.*;
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

    /**
     * Returns a 2D array of usernames, their top scores, and currentUsername with
     * currentScore from user data files in a directory.
     *
     * @param currentUsername The username of the current user.
     * @param currentScore    The score of the current user.
     * @return A 2D array (String[][]) where each row contains a username and their
     *         top score in descending order of score. The row containing
     *         currentUsername will have currentScore as its second column.
     * @throws IOException If an I/O error occurs when opening or reading a file.
     */
    public static String[][] getLeaderboard(String currentUsername, int currentScore) throws IOException {

        // Define the directory path
        String dir = "";

        // Initialize an ArrayList to store the leaderboard data
        List<String[]> leaderboardList = new ArrayList<>();

        // Add the current user's score to the leaderboardList
        leaderboardList.add(new String[] { "*" + currentUsername, String.valueOf(currentScore) });

        // Walk through the directory and process each file
        Files.walk(Paths.get(dir))
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    // Extract the username from the filename
                    String username = path.getFileName().toString().replace(".txt", "");

                    // Open the file and read its contents
                    try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {

                        // read first line and discard
                        reader.readLine();

                        String line;
                        // had to change this to 0 as it was displaying as MIN_VALUE when a user had no
                        // data
                        int maxScore = 0;

                        // Read each line in the file and update maxScore with the highest score found
                        while ((line = reader.readLine()) != null) {
                            // math.max simply returns the larger of two items a,b
                            maxScore = Math.max(maxScore, Integer.parseInt(line));
                        }

                        // to ensure if players currentScore is their new highscore not to show
                        // duplicate scores
                        // if the max score read in the userfile is not the same as the current users
                        // score
                        if (maxScore != currentScore && currentUsername != username) {

                            // Add the username and their top score to the leaderboardList
                            leaderboardList.add(new String[] { username, String.valueOf(maxScore) });

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        // Sort the leaderboardList in descending order of scores
        leaderboardList.sort((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));

        // Convert the leaderboardList to a 2D array
        String[][] leaderboard = new String[leaderboardList.size()][2];
        for (int i = 0; i < leaderboardList.size(); i++) {
            leaderboard[i] = leaderboardList.get(i);
        }

        // Return the sorted leaderboard with current user's score included
        return leaderboard;
    }

    /**
     * Returns a 2D array of usernames, their top scores, and (currentUsername with
     * currentScore)+(currentUsername2 with
     * currentScore2) from user data files in a directory.
     *
     * @param currentUsername The username of the current user.
     * @param currentScore    The score of the current user.
     * @return A 2D array (String[][]) where each row contains a username and their
     *         top score in descending order of
     *         score.
     *         The row containing currentUsername will have currentScore as its
     *         second column.
     * @throws IOException If an I/O error occurs when opening or reading a file.
     */
    public static String[][] getLeaderboard(String currentUsername, int currentScore, String currentUsername2,
            int currentScore2) throws IOException {

        // Define the directory path
        String dir = "";

        // Initialize an ArrayList to store the leaderboard data
        List<String[]> leaderboardList = new ArrayList<>();

        // Add the current user's score to the leaderboardList
        leaderboardList.add(new String[] { "1: " + currentUsername, String.valueOf(currentScore) });
        leaderboardList.add(new String[] { "2: " + currentUsername2, String.valueOf(currentScore2) });

        // Walk through the directory and process each file
        Files.walk(Paths.get(dir))
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    // Extract the username from the filename
                    String username = path.getFileName().toString().replace(".txt", "");

                    // Open the file and read its contents
                    try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {

                        // read first line and discard
                        reader.readLine();

                        String line;
                        // had to change this to 0 as it was displaying as MIN_VALUE when a user had no
                        // data
                        int maxScore = 0;

                        // Read each line in the file and update maxScore with the highest score found
                        while ((line = reader.readLine()) != null) {
                            // math.max simply returns the larger of two items a,b
                            maxScore = Math.max(maxScore, Integer.parseInt(line));
                        }

                        // to ensure if player one's or player two's current score is their new high
                        // score not to
                        // show duplicate scores
                        if (((maxScore != (currentScore) && maxScore != (currentScore2))
                                && (username != currentUsername))
                                && (username != currentUsername2)) {

                            // Add the username and their top score to the leaderboardList
                            leaderboardList.add(new String[] { username, String.valueOf(maxScore) });

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        // Sort the leaderboardList in descending order of scores
        leaderboardList.sort((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));

        // Convert the leaderboardList to a 2D array
        String[][] leaderboard = new String[leaderboardList.size()][2];
        for (int i = 0; i < leaderboardList.size(); i++) {
            leaderboard[i] = leaderboardList.get(i);
        }

        // Return the sorted leaderboard with current user's score included
        return leaderboard;
    }
}
