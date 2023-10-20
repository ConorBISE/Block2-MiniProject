package com.cd.quizwhiz;

// Import necessary Java libraries
import java.io.*;
import java.nio.file.*;
import java.util.*;

// Define the Leaderboard class
public class Leaderboard {
    // Define the getLeaderboard method that takes no inputs and returns a 2D array of usernames and their top scores
    public static String[][] getLeaderboard() throws IOException {
        // Define the directory path
        String dir = Auth.userFolder;

        // Initialize an ArrayList to store the leaderboard data
        List<String[]> leaderboardList = new ArrayList<>();

        // Walk through the directory and process each file
        Files.walk(Paths.get(dir))
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    // Extract the username from the filename
                    String username = path.getFileName().toString().replace(".txt", "");

                    // Open the file and read its contents
                    try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
                        reader.readLine();
                        String line;
                        int maxScore = Integer.MIN_VALUE;

                        // Read each line in the file and update maxScore with the highest score found
                        while ((line = reader.readLine()) != null) {
                            maxScore = Math.max(maxScore, Integer.parseInt(line));
                        }

                        // Add the username and their top score to the leaderboardList
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

        // Return the sorted leaderboard
        return leaderboard;
    }

    // Overloaded getLeaderboard method that takes a username and score as inputs
    public static String[][] getLeaderboard(String currentUsername, double currentScore) throws IOException {
        // Define the directory path
        String dir = Auth.userFolder;

        // Initialize an ArrayList to store the leaderboard data
        List<String[]> leaderboardList = new ArrayList<>();

        // Add the current user's score to the leaderboardList
        leaderboardList.add(new String[]{currentUsername + "a", String.valueOf(currentScore)});

        // Walk through the directory and process each file
        Files.walk(Paths.get(dir))
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    // Extract the username from the filename
                    String username = path.getFileName().toString().replace(".txt", "");

                    // Open the file and read its contents
                    try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
                        reader.readLine();
                        String line;
                        int maxScore = Integer.MIN_VALUE;

                        // Read each line in the file and update maxScore with the highest score found
                        while ((line = reader.readLine()) != null) {
                            maxScore = Math.max(maxScore, Integer.parseInt(line));
                        }

                        // Add the username and their top score to the leaderboardList
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

        // Return the sorted leaderboard with current user's score included
        return leaderboard;
    }
}
