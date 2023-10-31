package com.cd.quizwhiz.ui;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.Context;

import com.cd.quizwhiz.stats.Leaderboard;
import com.cd.quizwhiz.uiframework.UIEventListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;
import com.cd.quizwhiz.userstuff.User;

public class StatsPage extends UIPage<AppState> {
    private static final Logger logger = LoggerFactory.getLogger(StatsPage.class);

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
            int finalScore = user.finalScore();

            context.setVariable("score", finalScore);
            context.setVariable("scoreMessage", scoreMessages[finalScore]);
        }
        // Leaderboard
        try {
            String[][] leaderboard;
            if (this.justFinishedQuiz) {
                leaderboard = Leaderboard.getLeaderboard(user.getUsername(), user.finalScore());
            } else {
                leaderboard = Leaderboard.getLeaderboard();
            }

            context.setVariable("leaderboard", leaderboard);
        } catch (IOException e) {
            logger.error("Error while creating leaderboard: {}", e);
        }

        return true;
    }

    @UIEventListener(type = "click", id = "back-link")
    public void onBackLinkClick(UI<AppState> ui) {
        ui.loadPage(new HomePage());
    }
}