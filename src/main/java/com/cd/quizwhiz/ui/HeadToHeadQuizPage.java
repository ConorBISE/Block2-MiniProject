package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.questions.Player;
import com.cd.quizwhiz.questions.Question;
import com.cd.quizwhiz.questions.Switcher;
import com.cd.quizwhiz.uiframework.UI;

public class HeadToHeadQuizPage extends QuizPage {
    private Switcher currentPlayer;

    public HeadToHeadQuizPage(Question[] questionsToAsk) {
        super(questionsToAsk);

        // Override our superclass' instansiation of this - 
        // we want to go to HeadToHeadStatsPage afterwards, not StatsPage
        this.statsPage = new HeadToHeadStatsPage();
        this.currentPlayer = new Switcher();
    }

    @Override
    public boolean onPreload(UI<AppState> ui) {
        super.onPreload(ui);

        // If we don't have a second user: go have one log in
        // then come back here!
        if (ui.getState().multiplayerUserTwo == null) {
            ui.loadPage(new LoginPage(Player.Player2, this));
            return false;
        }

        ui.getContext().setVariable("multiplayer", true);

        return true;
    }

    @Override
    protected void loadQuestion(UI<AppState> ui, Question question) {
        super.loadQuestion(ui, question);

        // Every time a new question appears, we switch the active player
        // Update the UI text that informs the player whose turn it is
        AppState state = ui.getState();
        ui.setElementText("current-user", this.currentPlayer.getPlayer() == Player.Player1 ? state.user.getUsername()
                : state.multiplayerUserTwo.getUsername());
    }

    @Override
    protected void onAnswerClicked(UI<AppState> ui, int id) {
        super.onAnswerClicked(ui, id);

        // The other player's up!
        this.currentPlayer.switchCurrentPlayer();
    }

    @Override
    protected void incrementScore(UI<AppState> ui) {
        if (this.currentPlayer.getPlayer() == Player.Player1) {
            ui.getState().user.addScore();
        } else {
            ui.getState().multiplayerUserTwo.addScore();
        }
    }
}