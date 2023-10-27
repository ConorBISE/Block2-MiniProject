package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.Questions.Player;
import com.cd.quizwhiz.Questions.Question;
import com.cd.quizwhiz.uiframework.UI;

public class HeadToHeadQuizPage extends QuizPage {

    public HeadToHeadQuizPage(Question[] questionsToAsk) {
        super(questionsToAsk);
    }

    @Override
    public boolean onPreload(UI<AppState> ui) {
        if (ui.getState().multiplayerUserTwo == null) {
            ui.loadPage(new LoginPage(Player.player2, this));
            return false;
        }
        
        return true;
    }
}
