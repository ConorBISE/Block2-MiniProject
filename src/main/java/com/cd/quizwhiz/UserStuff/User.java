package com.cd.quizwhiz.userstuff;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cd.quizwhiz.stats.Statistics;

/**
 * The `User` class represents a user in the QuizWhiz application and provides
 * methods
 * for managing user data and statistics.
 */
public class User {
    // declare variables to store user data
    String username;
    int currentScore;

    private final Logger logger = LoggerFactory.getLogger(User.class);

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

    public void addScore()
    /**
     * Increases the user's current score by 1, typically used when a user gets an
     * answer correct.
     */
    {
        currentScore++;
    }

    // Used at the end of a quiz this method Gets the players current and final
    // score for the game and saves it to there user file , it the resets the score
    // back to zero for the next game

    public int finalScore()
    /**
     * Gets the player's current and final score for the game, saves it to their
     * user file,
     * and resets the score back to zero for the next game.
     *
     * @return The final score for the game.
     */

    {
        // Construct the filename based on the username.
        String userDataFileName = username + ".txt";
        File userFile = new File(Auth.userFolder, userDataFileName);
        int finalScore = 0;
        // If there is a file in the "users" folder with the given username.
        if (userFile.exists()) {
            // Try to write a new file with the user's data.
            try {
                // make a file writer
                FileWriter writer = new FileWriter(userFile, true);// --since i want to append to the file i
                // add a second parameter "true" to allow for appending instead of overwriting
                writer.write("\n" + currentScore);// append the users score to their userfile
                writer.close();// close my writier
                finalScore = currentScore;
                currentScore += 0;// reset user score
            } catch (IOException e) {
                // print out error
                logger.error("", e);
            }
        }
        // return the current
        return finalScore;
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
        File userFile = new File(Auth.userFolder, userDataFileName);

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
