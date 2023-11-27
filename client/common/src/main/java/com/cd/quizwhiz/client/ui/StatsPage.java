package com.cd.quizwhiz.client.ui;

import java.io.IOException;
import java.util.Map;

import com.cd.quizwhiz.client.stats.Leaderboard;
import com.cd.quizwhiz.client.uiframework.UIEventListener;
import com.cd.quizwhiz.client.uiframework.UI;
import com.cd.quizwhiz.client.uiframework.UIPage;
import com.cd.quizwhiz.client.user.User;

public class StatsPage extends UIPage<AppState> {
    private final boolean justFinishedQuiz;

    private static final String[] scoreMessages = new String[] {
            "keep trying - you got this",
            "keep trying - you got this",
            "you're getting it!",
            "you're getting it!",
            "impressive!",
            "impressive!",
            "you nailed it!"
    };

    public StatsPage(boolean justFinishedQuiz) {
        super("stats");
        this.justFinishedQuiz = justFinishedQuiz;
    }

    public StatsPage() {
        this(false);
    }

    @Override
    public boolean onPreload(UI<AppState> ui) {
        Map<String, Object> context = ui.getContext();
        User user = ui.getState().user;

        context.put("justFinishedQuiz", this.justFinishedQuiz);
        
        if (user.returnScores().length != 0) {
            context.put("userHasScores", true);
            context.put("userName", user.getUsername());
            context.put("userMean", String.format("%.2f", user.getMean()));
            context.put("userMedian", String.format("%.2f", user.getMedian()));
            context.put("userDeviation", String.format("%.2f", user.getDeviation()));
        } else {
            context.put("userHasScores", false);
        }

        if (justFinishedQuiz) {
            int finalScore = user.finalScore();

            context.put("score", finalScore);
            context.put("scoreMessage", scoreMessages[finalScore]);
        }

        // Leaderboard
        try {
            String[][] leaderboard;

            // If we've just finished a quiz: we want to show the user not only their maximum score
            // but also the score of the game they've just finished
            if (this.justFinishedQuiz) {
                leaderboard = Leaderboard.getLeaderboard(user.getUsername(), user.finalScore());
            } else {
                leaderboard = Leaderboard.getLeaderboard();
            }

            context.put("leaderboard", leaderboard);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @UIEventListener(type = "click", id = "back-link")
    public void onBackLinkClick(UI<AppState> ui) {
        ui.loadPage(new HomePage());
    }
}