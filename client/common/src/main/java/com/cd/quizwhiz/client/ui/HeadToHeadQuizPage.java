package com.cd.quizwhiz.client.ui;

import com.cd.quizwhiz.common.questions.Question;

import java.util.function.Consumer;

import com.cd.quizwhiz.client.questions.Player;
import com.cd.quizwhiz.client.questions.Switcher;
import com.cd.quizwhiz.client.uiframework.UI;

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
    public void onPreload(UI<AppState> ui, Consumer<Boolean> callback) {
        // If we don't have a second user: go have one log in
        // then come back here!
        if (ui.getState().multiplayerUserTwo == null) {
            ui.loadPage(new LoginPage(Player.Player2, this, "to continue to Head-To-Head"));
            callback.accept(false);
            return;
        }

        ui.getContext().put("multiplayer", true);

        callback.accept(true);
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
            ui.getState().user.incrementScore();
        } else {
            ui.getState().multiplayerUserTwo.incrementScore();
        }
    }

    @Override
    protected void endGame(UI<AppState> ui) {
        super.endGame(ui);
        ui.getState().multiplayerUserTwo = null;
    }
}