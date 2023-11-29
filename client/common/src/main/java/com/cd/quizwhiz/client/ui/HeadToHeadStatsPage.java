package com.cd.quizwhiz.client.ui;

import java.util.Map;
import java.util.function.Consumer;

import com.cd.quizwhiz.client.stats.Leaderboard;
import com.cd.quizwhiz.client.uiframework.UI;
import com.cd.quizwhiz.client.user.UserSession;

public class HeadToHeadStatsPage extends StatsPage {
    public HeadToHeadStatsPage() {
        super(true);
    }

    @Override
    public void onPreload(UI<AppState> ui, Consumer<Boolean> callback) {
        UserSession primaryUser = ui.getState().user;
        UserSession secondaryUser = ui.getState().multiplayerUserTwo;

        primaryUser.saveScore(ui.getNetClient(), () -> {
            secondaryUser.saveScore(ui.getNetClient(), () -> {
                Map<String, Object> context = ui.getContext();

                // The stats template uses the multiplayer variable to toggle the top portion
                // of the stats page between showing a single user score, and multiple user
                // scores
                context.put("multiplayer", true);
                context.put("multiplayerUserName", secondaryUser.getUsername());

                // Our superclass will already have called FinalScore on our primary user, and
                // stored it in context. To get their score for the purpose of leaderboarding,
                // we'll have to extract it back out.
                int primaryUserFinalScore = (int) context.get("score");
                int secondaryUserFinalScore = ui.getState().multiplayerUserTwo.getCurrentScore();

                context.put("multiplayerUserTwoScore", secondaryUserFinalScore);

                // This leaderboard has every player's maximum score, along with the scores
                // both users just got in this match
                /*
                 * String[][] leaderboard =
                 * Leaderboard.getLeaderboard(primaryUser.getUsername(),
                 * primaryUserFinalScore,
                 * secondaryUser.getUsername(), secondaryUserFinalScore);
                 */

                Leaderboard.getLeaderboard(ui.getNetClient(), (leaderboard) -> {
                    context.put("leaderboard", leaderboard);

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

                    primaryUser.resetScore();
                    secondaryUser.resetScore();

                    callback.accept(true);
                });
            });
        });
    }

}