package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.UserStuff.User;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;

public class StatsPage extends UIPage<AppState> {

    private boolean justFinishedQuiz;

    private static String[] scoreMessages = new String[] {
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
    public void onStart(UI<AppState> ui) {
        User user = ui.getState().user;

        if (justFinishedQuiz) {
            int finalScore = ui.getState().user.FinalScore();
            
            ui.setElementText("score", Integer.toString(finalScore));
            ui.setElementText("score-message", scoreMessages[finalScore]);
            ui.setElementVisibility("just-finished-quiz-section", true);
        }

        ui.setElementText("mean", String.format("%.2f", user.GetMean()));
        ui.setElementText("median", String.format("%.2f", user.GetMedian()));
        ui.setElementText("std-dev", String.format("%.2f", user.GetDeviation()));
    }

}