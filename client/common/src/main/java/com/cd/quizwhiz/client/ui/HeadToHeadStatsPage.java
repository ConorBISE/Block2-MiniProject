package com.cd.quizwhiz.client.ui;

import java.io.IOException;
import java.util.Map;

import com.cd.quizwhiz.client.stats.Leaderboard;
import com.cd.quizwhiz.client.uiframework.UI;
import com.cd.quizwhiz.client.user.User;

public class HeadToHeadStatsPage extends StatsPage {
    public HeadToHeadStatsPage() {
        super(true);
    }

    @Override
    public boolean onPreload(UI<AppState> ui) {
        super.onPreload(ui);

        User primaryUser = ui.getState().user;
        User secondaryUser = ui.getState().multiplayerUserTwo;

        Map<String, Object> context = ui.getContext();
        
        // The stats template uses the multiplayer variable to toggle the top portion
        // of the stats page between showing a single user score, and multiple user scores
        context.put("multiplayer", true);
        context.put("multiplayerUserName", secondaryUser.getUsername());

        // Our superclass will already have called FinalScore on our primary user, and
        // stored it in context. To get their score for the purpose of leaderboarding,
        // we'll have to extract it back out.
        int primaryUserFinalScore = (int) context.get("score");
        int secondaryUserFinalScore = ui.getState().multiplayerUserTwo.finalScore();

        context.put("multiplayerUserTwoScore", secondaryUserFinalScore);

        try {
            // This leaderboard has every player's maximum score, along with the scores
            // both users just got in this match
            String[][] leaderboard = Leaderboard.getLeaderboard(primaryUser.getUsername(), primaryUserFinalScore,
                    secondaryUser.getUsername(), secondaryUserFinalScore);

            context.put("leaderboard", leaderboard);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Override the score message to congratulate the victor
        String scoreMessage;
        if (primaryUserFinalScore > secondaryUserFinalScore) {
            scoreMessage = primaryUser.getUsername() + " takes it!";
        } else if (primaryUserFinalScore < secondaryUserFinalScore) {
            scoreMessage = secondaryUser.getUsername() + " takes it!";
        } else {
            scoreMessage = "it's a draw";
        }

        context.put("scoreMessage", scoreMessage);

        return true;
    }

}