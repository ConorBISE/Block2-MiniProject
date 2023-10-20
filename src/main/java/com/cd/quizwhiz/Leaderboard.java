package com.cd.quizwhiz;

// Import necessary Java libraries
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Leaderboard {
    // Define the getLeaderboard method that takes no inputs and returns a 2D array of usernames and their top scores
    public static String[][] getLeaderboard() throws IOException {
        String dir = Auth.userFolder; // Use Auth.UserFolder as the directory path
        List<String[]> leaderboardList = new ArrayList<>();
        Files.walk(Paths.get(dir))
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    String username = path.getFileName().toString().replace(".txt", "");
                    try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
                        reader.readLine();
                        String line;
                        int maxScore = Integer.MIN_VALUE;
                        while ((line = reader.readLine()) != null) {
                            maxScore = Math.max(maxScore, Integer.parseInt(line));
                        }
                        leaderboardList.add(new String[]{username, String.valueOf(maxScore)});
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
        return leaderboard;
    }
}
