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
        this.currentPlayer = new Switcher();
    }

    @Override
    public boolean onPreload(UI<AppState> ui) {
        if (ui.getState().multiplayerUserTwo == null) {
            ui.loadPage(new LoginPage(Player.player2, this));
            return false;
        }

        ui.getContext().setVariable("multiplayer", true);

        return true;
    }

    @Override
    protected void loadQuestion(UI<AppState> ui, Question question) {
        super.loadQuestion(ui, question);
        
        AppState state = ui.getState();
        ui.setElementText("current-user", this.currentPlayer.getPlayer() == Player.player1 ? state.user.getUsername() : state.multiplayerUserTwo.getUsername());
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