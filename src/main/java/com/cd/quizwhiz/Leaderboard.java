package com.cd.quizwhiz;

// Import necessary Java libraries
import java.io.*;
import java.nio.file.*;
import java.util.*;

// Define the Leaderboard class
public class Leaderboard {
    // Define the main method
    public static void main(String[] args) throws IOException {
        // Call the getLeaderboard method to get a map of usernames and their top scores
        Map<String, Integer> leaderboard = getLeaderboard("users");
        // Sort the entries in the map by value in descending order and print them
        leaderboard.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(System.out::println);
    }

    // Define the getLeaderboard method that takes a directory path as input and returns a map of usernames and their top scores
    private static Map<String, Integer> getLeaderboard(String dir) throws IOException {
        // Initialize an empty map to store the leaderboard
        Map<String, Integer> leaderboard = new HashMap<>();
        // Walk through all files in the given directory
        Files.walk(Paths.get(dir))
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    // Get the username from the filename by removing the ".txt" extension
                    String username = path.getFileName().toString().replace(".txt", "");
                    // Use a try-with-resources block to open and read the file
                    try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
                        reader.readLine(); // Skip password line
                        String line;
                        int maxScore = Integer.MIN_VALUE;
                        // Read each line in the file (after the password line)
                        while ((line = reader.readLine()) != null) {
                            // Update maxScore if the current score is higher than maxScore
                            maxScore = Math.max(maxScore, Integer.parseInt(line));
                        }
                        // Add the username and their top score to the leaderboard map
                        leaderboard.put(username, maxScore);
                    } catch (IOException e) {
                        // Print any IOExceptions that occur while reading the file
                        e.printStackTrace();
                    }
                });
        // Return the completed leaderboard map
        return leaderboard;
    }
}
