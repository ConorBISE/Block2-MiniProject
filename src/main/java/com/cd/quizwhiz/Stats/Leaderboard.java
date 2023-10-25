package com.cd.quizwhiz.Stats;

// Import necessary Java libraries
import com.cd.quizwhiz.UserStuff.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * The `Leaderboard` class provides methods for retrieving and managing user leaderboards,
 * including sorting users by their top scores and including the current user's score.
 */
public class Leaderboard {

    private static final Logger logger = LoggerFactory.getLogger(Auth.class);



    /**
     * Returns a 2D array of usernames and their top scores from user data files in a directory.
     *
     * @return A 2D array (String[][]) where each row contains a username and their top score in descending order of score.
     * @throws IOException If an I/O error occurs when opening or reading a file.
     */
    public static String[][] getLeaderboard() throws IOException {
        // Define the directory path
        String dir = Auth.userFolder;

        // Initialize an ArrayList to store the leaderboard data
        List<String[]> leaderboardList = new ArrayList<>();

        // Walk through the directory and process each file
        Files.walk(Paths.get(dir)).filter(Files::isRegularFile).forEach(path -> {

                    // Extract the username from the filename
                    String username = path.getFileName().toString().replace(".txt", "");

                    // Open the file and read its contents
                    try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
                        reader.readLine();
                        String line;
                        int maxScore = 0;

                        // Read each line in the file and update maxScore with the highest score found
                        while ((line = reader.readLine()) != null) {
                            maxScore = Math.max(maxScore, Integer.parseInt(line));
                        }

                        // Add the username and their top score to the leaderboardList
                        leaderboardList.add(new String[]{username, String.valueOf(maxScore)});
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                });

        // Sort the leaderboardList in descending order of scores
        leaderboardList.sort((a, b) -> Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));


        // Convert the leaderboardList to a 2D array

            //creates string array with a row for each pair in leaderboard and 2 columns
        String[][] leaderboard = new String[leaderboardList.size()][2];

            //for each item in leaderboard add it to our 2D array
        for (int i = 0; i < leaderboardList.size(); i++) {
            leaderboard[i] = leaderboardList.get(i);
        }

        // Return the sorted leaderboard
        return leaderboard;
    }



    /**
     * Returns a 2D array of usernames, their top scores, and currentUsername with currentScore from user data files in a directory.
     *
     * @param currentUsername The username of the current user.
     * @param currentScore The score of the current user.
     * @return A 2D array (String[][]) where each row contains a username and their top score in descending order of score. The row containing currentUsername will have currentScore as its second column.
     * @throws IOException If an I/O error occurs when opening or reading a file.
     */
    public static String[][] getLeaderboard(String currentUsername, int currentScore) throws IOException {

        // Define the directory path
        String dir = Auth.userFolder;

        // Initialize an ArrayList to store the leaderboard data
        List<String[]> leaderboardList = new ArrayList<>();

        // Add the current user's score to the leaderboardList
        leaderboardList.add(new String[]{"*"+currentUsername , String.valueOf(currentScore)});

        // Walk through the directory and process each file
        Files.walk(Paths.get(dir))
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    // Extract the username from the filename
                    String username = path.getFileName().toString().replace(".txt", "");

                    // Open the file and read its contents
                    try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {

                            //read first line and discard
                        reader.readLine();

                        String line;
                        //had to change this to 0 as it was displaying as MIN_VALUE when a user had no data
                        int maxScore = 0;

                        // Read each line in the file and update maxScore with the highest score found
                        while ((line = reader.readLine()) != null) {
                            //math.max simply returns the larger of two items a,b
                            maxScore = Math.max(maxScore, Integer.parseInt(line));
                        }

                        // Add the username and their top score to the leaderboardList
                        leaderboardList.add(new String[]{username, String.valueOf(maxScore)});
                    } catch (IOException e) {
                        logger.error("", e);
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
