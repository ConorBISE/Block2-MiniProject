package com.cd.quizwhiz;

import java.io.IOException;
import java.util.Map;

public class main {
    public static void main(String[] args) {
        User user1 = new User("jondo");
        user1.AddScore();
        user1.AddScore();
        user1.FinalScore();
        user1.AddScore();
        user1.FinalScore();
        System.out.println(user1.GetMean());
        System.out.println(user1.GetDeviation());
        System.out.println(user1.GetMedian());

        try
            {
                String[][] leaderboard = Leaderboard.getLeaderboard();
                // Now you can use the leaderboard 2D array
                for (String[] user : leaderboard)
                    {
                        System.out.println(user[0] + ": " + user[1]);
                    }
            }
        catch (IOException e)
            {
                e.printStackTrace();
            }
    }
}



