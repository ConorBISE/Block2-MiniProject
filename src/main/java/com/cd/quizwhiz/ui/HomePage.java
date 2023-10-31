package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.Questions.Category;
import com.cd.quizwhiz.Questions.QuestionBank;
import com.cd.quizwhiz.Questions.Player;
import com.cd.quizwhiz.uiframework.UIEventListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;

public class HomePage extends UIPage<AppState> {
    public HomePage() {
        super("home");        
    }

    @Override
    public boolean onPreload(UI<AppState> ui) {
        ui.setTitle("quizwhiz");
        ui.setIcon("/images/logo.jpg");
        
        if (ui.getState().user == null) {
            ui.loadPage(new LoginPage(Player.player1, this));
            return false;
        }
        
        return true;
    }

    @Override
    public void onStart(UI<AppState> ui) {
        ui.setElementText("username", ui.getState().user.getUsername());
    }

    @UIEventListener(type="change", id="quiz-mode")
    public void onQuizModeChange(UI<AppState> ui) {
        System.out.println("change!");
        String modeString = ui.getInputValueById("quiz-mode");
        ui.setElementVisibility("quiz-category", !modeString.equals("head-to-head"));
    }

    @UIEventListener(type="click", id="go-button")
    public void onQuizButtonClick(UI<AppState> ui) {
        // What kind of quiz does the user want to do?
        String categoryString = ui.getInputValueById("quiz-category");
        String modeString = ui.getInputValueById("quiz-mode");
        
        Category category = null;
        
        switch (categoryString) {
            case "comp-org":
                category = Category.ComputerOrg;
                break;

            case "discrete-maths":
                category = Category.DiscreteMaths;
                break;

            case "comp-sci":
                category = Category.ComputerSci;
                break;
        }

        UIPage<AppState> nextPage = null;

        switch (modeString) {
            case "increasing-difficulty":
                nextPage = new QuizPage(QuestionBank.IncDifficulty(category));
                break;
            
            case "random-draw":
                nextPage = new QuizPage(QuestionBank.RandomQuestion(category));
                break;
                
            case "head-to-head":
                nextPage = new HeadToHeadQuizPage(QuestionBank.Coop());
                break;
        }

        ui.loadPage(nextPage);
    }

    @UIEventListener(type="click", id="stats-link")
    public void onStatsLinkClicked(UI<AppState> ui) {
        ui.loadPage(new StatsPage());
    }

    @UIEventListener(type="click", id="about-link")
    public void onAboutPageClicked(UI<AppState> ui) {
        ui.loadPage(new AboutPage());
    }
}
