package com.cd.quizwhiz.ui;

import java.io.IOException;

import org.thymeleaf.context.Context;

import com.cd.quizwhiz.Stats.Leaderboard;
import com.cd.quizwhiz.UserStuff.User;
import com.cd.quizwhiz.uiframework.ClickListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;

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
        Context context = ui.getContext();
        User user = ui.getState().user;

        context.setVariable("justFinishedQuiz", this.justFinishedQuiz);
        context.setVariable("user", user);

        if (justFinishedQuiz) {
            int finalScore = user.FinalScore();

            context.setVariable("score", Integer.toString(finalScore));
            context.setVariable("scoreMessage", scoreMessages[finalScore]);
        }
        // Leaderboard
        try {
            String[][] leaderboard = Leaderboard.getLeaderboard(user.getUsername(), user.FinalScore());
            context.setVariable("leaderboard", leaderboard);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @ClickListener(id="back-link")
    public void onBackLinkClick(UI<AppState> ui) {
        ui.loadPage(new HomePage());
    }
}