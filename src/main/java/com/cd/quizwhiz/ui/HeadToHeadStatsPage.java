package com.cd.quizwhiz.ui;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.Context;

import com.cd.quizwhiz.stats.Leaderboard;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.userstuff.User;

public class HeadToHeadStatsPage extends StatsPage {
    private static final Logger logger = LoggerFactory.getLogger(StatsPage.class);

    public HeadToHeadStatsPage() {
        super(true);
    }

    @Override
    public boolean onPreload(UI<AppState> ui) {
        super.onPreload(ui);

        User primaryUser = ui.getState().user;
        User secondaryUser = ui.getState().multiplayerUserTwo;

        Context context = ui.getContext();
        
        // The stats template uses the multiplayer variable to toggle the top portion
        // of the stats page between showing a single user score, and multiple user scores
        context.setVariable("multiplayer", true);
        context.setVariable("multiplayerUserTwo", secondaryUser);

        // Our superclass will already have called FinalScore on our primary user, and
        // stored it in context. To get their score for the purpose of leaderboarding,
        // we'll have to extract it back out.
        int primaryUserFinalScore = (int) context.getVariable("score");
        int secondaryUserFinalScore = ui.getState().multiplayerUserTwo.finalScore();

        context.setVariable("multiplayerUserTwoScore", secondaryUserFinalScore);

        try {
            // This leaderboard has every player's maximum score, along with the scores
            // both users just got in this match
            String[][] leaderboard = Leaderboard.getLeaderboard(primaryUser.getUsername(), primaryUserFinalScore,
                    secondaryUser.getUsername(), secondaryUserFinalScore);

            context.setVariable("leaderboard", leaderboard);
        } catch (IOException e) {
            logger.error("Error while creating leaderboard: {}", e);
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

        context.setVariable("scoreMessage", scoreMessage);

        return true;
    }

}