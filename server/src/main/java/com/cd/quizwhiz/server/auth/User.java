package com.cd.quizwhiz.server.auth;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.cd.quizwhiz.server.stats.Statistics;

/**
 * The `User` class represents a user in the QuizWhiz application and provides
 * methods
 * for managing user data and statistics.
 */ 
public class User {
    // declare variables to store user data
    String username;

    // intizilization method takes in an String argument username
    public User(String username)
    /**
     * Initializes a new user with the given username.
     *
     * @param username The username for the new user.
     */
    {
        this.username = username;
    }

    public void finalScore(int score)
    /**
     * Saves a player's final score to their user file
     */
    {
        // Construct the filename based on the username.
        String userDataFileName = username + ".txt";
        File userFolder = new File(Auth.userFolder);

        if (!userFolder.exists())
            userFolder.mkdir();

        File userFile = new File(userFolder, userDataFileName);
        // If there is a file in the "users" folder with the given username.
        if (userFile.exists()) {
            // Try to write a new file with the user's data.
            try {
                // make a file writer
                FileWriter writer = new FileWriter(userFile, true);// --since i want to append to the file i
                // add a second parameter "true" to allow for appending instead of overwriting
                writer.write("\n" + score);// append the users score to their userfile
                writer.close();// close my writier
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public double[] returnScores()
    /**
     * Returns an array of the user's scores stored in their user file.
     *
     * @return An array of user scores as doubles.
     */

    {
        // Create a list to temporarily store user scores as doubles.
        List<Double> scoresList = new ArrayList<>();

        String userDataFileName = username + ".txt";
        File userFolder = new File(Auth.userFolder);
        
        if (!userFolder.exists())
            userFolder.mkdir();

        File userFile = new File(userFolder, userDataFileName);


        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line = reader.readLine();
            ; // Read and discard the first line as it is our password.

            while ((line = reader.readLine()) != null) {
                double score = Double.parseDouble(line);
                scoresList.add(score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the list of double scores to a double[] array.
        double[] scoresArray = new double[scoresList.size()];
        for (int i = 0; i < scoresList.size(); i++) {
            scoresArray[i] = scoresList.get(i);
        }

        return scoresArray;
    }

    public double getMean()
    /**
     * Calculates and returns the mean (average) of the user's scores.
     *
     * @return The mean of the user's scores.
     */

    {
        double[] scores = returnScores();
        return Statistics.mean(scores);
    }

    public double getMedian()
    /**
     * Calculates and returns the median of the user's scores.
     *
     * @return The median of the user's scores.
     */

    {
        double[] scores = returnScores();
        return Statistics.median(scores);
    }

    public double getDeviation()
    /**
     * Calculates and returns the standard deviation of the user's scores.
     *
     * @return The standard deviation of the user's scores.
     */
    {
        double[] scores = returnScores();
        return Statistics.standardDeviation(scores);
    }

    public String getUsername()
    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    {
        return username;
    }

}
