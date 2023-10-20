package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.Questions.QuestionBank;
import com.cd.quizwhiz.uiframework.ClickListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;

public class HomePage extends UIPage<AppState> {
    public HomePage() {
        super("home");        
    }

    @Override
    public boolean onPreload(UI<AppState> ui) {
        ui.setTitle("quizwhiz");
        
        if (ui.getState().user == null) {
            ui.loadPage(new LoginPage());
            return false;
        }
        
        return true;
    }

    @ClickListener(id="quiz-button")
    public void onQuizButtonClick(UI<AppState> ui) {
       ui.loadPage(new QuizPage(QuestionBank.QUESTIONS));
    }
}
