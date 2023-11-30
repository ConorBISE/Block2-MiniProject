package com.cd.quizwhiz.server.auth;

import java.io.*;
import java.util.List;

import com.cd.quizwhiz.server.stats.Statistics;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The `User` class represents a user in the QuizWhiz application and provides
 * methods
 * for managing user data and statistics.
 */
public class User {
    // declare variables to store user data
    @JsonProperty
    private String username;
    
    @JsonProperty 
    private String hashedPassword;
    
    @JsonProperty
    private List<Double> scores;

    // intizilization method takes in an String argument username
    public User(@JsonProperty("username") String username, @JsonProperty("hashedPassword") String hashedPassword, @JsonProperty("scores") List<Double> scores)
    /**
     * Initializes a new user with the given username.
     *
     * @param username The username for the new user.
     */
    {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.scores = scores;
    }

    public static File getUserFile(String username) {
        String userDataFileName = username + ".json";
        File userFolder = new File(Auth.userFolder);

        if (!userFolder.exists())
            userFolder.mkdir();

        return new File(userFolder, userDataFileName);
    }

    public static User readUserFromFile(String username) throws StreamReadException, DatabindException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(User.getUserFile(username), User.class);
    }

    public void save() throws StreamWriteException, DatabindException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(User.getUserFile(this.username), this);        
    }

    @JsonIgnore
    public void appendFinalScore(int score)
    /**
     * Saves a player's final score to their user file
     */
    {
        scores.add((double) score);
    }

    @JsonIgnore
    public double[] returnScores()
    /**
     * Returns an array of the user's scores stored in their user file.
     *
     * @return An array of user scores as doubles.
     */

    {
        return scores.stream().mapToDouble(Double::doubleValue).toArray(); 
    }

    @JsonIgnore
    public double getMean()
    /**
     * Calculates and returns the mean (average) of the user's scores.
     *
     * @return The mean of the user's scores.
     */

    {
        return Statistics.mean(returnScores());
    }

    @JsonIgnore
    public double getMedian()
    /**
     * Calculates and returns the median of the user's scores.
     *
     * @return The median of the user's scores.
     */

    {
        return Statistics.median(returnScores());
    }

    @JsonIgnore
    public double getDeviation()
    /**
     * Calculates and returns the standard deviation of the user's scores.
     *
     * @return The standard deviation of the user's scores.
     */
    {
        return Statistics.standardDeviation(returnScores());
    }

    @JsonIgnore
    public String getUsername()
    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    {
        return username;
    }

    @JsonIgnore
    public String getHashedPassword() {
        return hashedPassword;
    }
}
