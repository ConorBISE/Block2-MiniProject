package com.cd.quizwhiz;

public class main {
    public static void main(String[] args){
        User user1 = new User("jondo");
        user1.AddScore();
        user1.AddScore();
        user1.FinalScore();
        user1.AddScore();
        user1.FinalScore();
        System.out.println(user1.GetMean());
        System.out.println(user1.GetDeviation());
        System.out.println(user1.GetMedian());
        System.out.println(new Leaderboard());
    }
}
