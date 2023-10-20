package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.Questions.Category;
import com.cd.quizwhiz.Questions.Question;
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

    @Override
    public void onStart(UI<AppState> ui) {
        ui.setElementText("username", ui.getState().user.getUsername());
    }

    @ClickListener(id="go-button")
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

        Question[] questions = null;

        switch (modeString) {
            case "increasing-difficulty":
                questions = QuestionBank.IncDifficulty(category);
                break;
            
            case "random-draw":
                questions = QuestionBank.RandomQuestion(category);
                break;    
        }

        ui.loadPage(new QuizPage(questions));
    }
}
