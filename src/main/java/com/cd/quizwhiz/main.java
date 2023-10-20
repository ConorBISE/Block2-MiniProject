package com.cd.quizwhiz;

import com.cd.quizwhiz.Stats.Leaderboard;
import com.cd.quizwhiz.UserStuff.Auth;
import com.cd.quizwhiz.UserStuff.PasswordEncryption;
import com.cd.quizwhiz.UserStuff.User;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws Exception {
       String user11= Auth.register("jonny","1233");
        System.out.println(user11);
        User user1 = new User(user11);
        System.out.println(Auth.login("jonny","1233"));
        int count = 1;
        for(String[] i:Leaderboard.getLeaderboard("user1",8)) {
            System.out.println(count+": "+i[0]+"-"+i[1]);
            count++;
        }
    }
}



