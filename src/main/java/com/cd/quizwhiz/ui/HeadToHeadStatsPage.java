package com.cd.quizwhiz.ui;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.Context;

import com.cd.quizwhiz.Stats.Leaderboard;
import com.cd.quizwhiz.UserStuff.User;
import com.cd.quizwhiz.uiframework.UI;

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
        context.setVariable("multiplayer", true);
        context.setVariable("multiplayerUserTwo", secondaryUser);        
        
        // Our superclass will already have called FinalScore on our primary user
        // To get their score for the purpose of leaderboarding, we'll have to extract it from the context
        int primaryUserFinalScore = (int) context.getVariable("score");
        int secondaryUserFinalScore = ui.getState().multiplayerUserTwo.FinalScore();

        context.setVariable("multiplayerUserTwoScore", secondaryUserFinalScore);

        try {
            String[][] leaderboard = Leaderboard.getLeaderboard(primaryUser.getUsername(), primaryUserFinalScore, 
                    secondaryUser.getUsername(), secondaryUserFinalScore);

            context.setVariable("leaderboard", leaderboard);
        } catch (IOException e) {
            logger.error("Error while creating leaderboard: {}", e);
        }

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