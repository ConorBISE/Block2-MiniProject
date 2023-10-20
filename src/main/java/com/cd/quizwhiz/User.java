package com.cd.quizwhiz;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User
{
    //declare variables to store user data
    String username;
    int currentScore;

    //intizilization method takes in an String argument username
    public User(String username)
        {
            this.username = username;
        }

    //add score used everytime a user gets an answer correct to increment score
    public void AddScore()
        {
            currentScore++;
        }

    //Used at the end of a quiz this method Gets the players current and final
    // score for the game and saves it to there user file , it the resets the score back to zero for the next game

    public int FinalScore()
    {
        // Construct the filename based on the username.
        String userDataFileName = username + ".txt";
        File userFile = new File(Auth.userFolder, userDataFileName);
        int finalScore = 0;
        // If there is a file in the "users" folder with the given username.
        if (userFile.exists())
        {
            // Try to write a new file with the user's data.
            try
                {
                    //make a file  writer
                    FileWriter writer = new FileWriter(userFile, true);// --since i want to append to the file i
                    // add a second parameter "true" to allow for appending instead of overwriting
                    writer.write("\n" + currentScore);// append the users score to their userfile
                    writer.close();//close my writier
                    finalScore = currentScore;
                    currentScore+=0;//reset user score
                }
            catch (IOException e)
                {
                    //print out error
                    System.out.println(e);
                }
        }
        //return the current
        return finalScore;
    }

    public double[] ReturnScores() {
        // Create a list to temporarily store user scores as doubles.
        List<Double> scoresList = new ArrayList<>();

        String userDataFileName = username + ".txt";
        File userFile = new File(Auth.userFolder, userDataFileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line = reader.readLine(); // Read and discard the first line as it is our password.

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

    public double GetMean(){
      double[] scores = ReturnScores();
      return  Statistics.Mean(scores);
    }
    public double GetMedian(){
        double[] scores = ReturnScores();
        return  Statistics.Median(scores);
    }
    public double GetDeviation(){
        double[] scores = ReturnScores();
        return  Statistics.StandardDeviation(scores);
    }

    public String getUsername() { return username; }
}
