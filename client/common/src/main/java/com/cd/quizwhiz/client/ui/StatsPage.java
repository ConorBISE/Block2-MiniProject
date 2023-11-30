package com.cd.quizwhiz.client.ui;

import java.util.Map;
import java.util.function.Consumer;

import com.cd.quizwhiz.client.stats.Leaderboard;
import com.cd.quizwhiz.client.uiframework.UI;
import com.cd.quizwhiz.client.uiframework.UIPage;
import com.cd.quizwhiz.client.user.UserSession;

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
    public void onPreload(UI<AppState> ui, Consumer<Boolean> callback) {
        Map<String, Object> context = ui.getContext();
        UserSession user = ui.getState().user;

        context.put("justFinishedQuiz", this.justFinishedQuiz);

        Runnable preloadInner = () -> {
            user.getStats(ui.getNetClient(), (stats) -> {
                if (stats.hasStats) {
                    context.put("userHasScores", true);
                    context.put("userName", user.getUsername());
                    context.put("userMean", formatStat(stats.mean, 2));
                    context.put("userMedian", formatStat(stats.median, 2));
                    context.put("userDeviation", formatStat(stats.deviation, 2));
                } else {
                    context.put("userHasScores", false);
                }

                if (justFinishedQuiz) {
                    context.put("score", user.getCurrentScore());
                    context.put("scoreMessage", scoreMessages[user.getCurrentScore()]);
                }

                // Leaderboard
                // If we've just finished a quiz: we want to show the user not only their
                // maximum score
                // but also the score of the game they've just finished
                // if (this.justFinishedQuiz) {
                // leaderboard = Leaderboard.getLeaderboard(user.getUsername(),
                // user.saveScore());
                // } else {

                Leaderboard.getLeaderboard(ui.getNetClient(), (leaderboard) -> {
                    context.put("leaderboard", leaderboard);
                    callback.accept(true);
                });
            });
        };
        
        if (justFinishedQuiz) {
            user.saveScore(ui.getNetClient(), preloadInner);
        } else {
            preloadInner.run();
        }
    }

    @Override
    public void onStart(UI<AppState> ui) {
        ui.addListener("back-link", "click", e -> this.onBackLinkClick(ui));
    }

    public void onBackLinkClick(UI<AppState> ui) {
        ui.loadPage(new HomePage());
    }

    private static String formatStat(double stat, int maxDecimalPlaces) {
        String val = String.valueOf(stat);
        int dotPos = val.indexOf(".");
        int numDecimalPlaces = val.length() - dotPos - 1;

        if (numDecimalPlaces > maxDecimalPlaces) {
            return val.substring(0, dotPos + maxDecimalPlaces + 1);
        }

        return val;
    }
}