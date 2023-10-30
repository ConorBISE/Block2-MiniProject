package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.Questions.Player;
import com.cd.quizwhiz.Questions.Question;
import com.cd.quizwhiz.Questions.Switcher;
import com.cd.quizwhiz.uiframework.UI;

public class HeadToHeadQuizPage extends QuizPage {

    Switcher currentPlayer;

    public HeadToHeadQuizPage(Question[] questionsToAsk) {
        super(questionsToAsk);
        this.statsPage = new HeadToHeadStatsPage();
    }

    @Override
    public boolean onPreload(UI<AppState> ui) {
        if (ui.getState().multiplayerUserTwo == null) {
            ui.loadPage(new LoginPage(Player.player2, this));
            return false;
        }

        return true;
    }

    @Override
    public void onStart(UI<AppState> ui) {
        super.onStart(ui);
        this.currentPlayer = new Switcher();
    }

    @Override
    protected void onAnswerClicked(UI<AppState> ui, int id) {
        super.onAnswerClicked(ui, id);
        this.currentPlayer.Switch();
    }

    @Override
    protected void incrementScore(UI<AppState> ui) {
        if (this.currentPlayer.getPlayer() == Player.player1) {
            ui.getState().user.AddScore();
        } else {
            ui.getState().multiplayerUserTwo.AddScore();
        }
    }
}