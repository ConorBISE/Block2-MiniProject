package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.Questions.Question;
import com.cd.quizwhiz.uiframework.ClickListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;

public class QuizPage extends UIPage<AppState> {
    private Question[] questionsToAsk;
    private int currentQuestionIndex = 0;
    
    public QuizPage(Question[] questionsToAsk) {
        super("quiz");

        this.questionsToAsk = questionsToAsk;
    }

    @Override
    public void onStart(UI<AppState> ui) {
        this.loadQuestion(ui, this.questionsToAsk[0]);

        for (int i = 0; i < 4; i++) {
            int finalI = i;
            ui.addClickListener("answer-" + i, event -> {
                this.onAnswerClicked(ui, finalI);
            });
        }
    };

    private void loadQuestion(UI<AppState> ui, Question question) {
        ui.setElementText("question-text", question.getQuestion());

        for (int i = 0; i < 4; i++) {
            ui.setElementText("answer-" + i, question.getAnswers()[i]);
        }

        ui.setElementVisibility("feedback-toast", false);
        ui.setElementVisibility("next-button", false);
    }

    public void onAnswerClicked(UI<AppState> ui, int id) {
        Question currentQuestion = this.questionsToAsk[this.currentQuestionIndex];

        if (id == currentQuestion.getActualAnswer()) {
            ui.setElementText("feedback-toast", "Correct!");
            ui.setElementClasses("feedback-toast", "toast good");
        } else {
            ui.setElementText("feedback-toast", "Incorrect! The right answer was: " + currentQuestion.getAnswers()[currentQuestion.getActualAnswer()]);
            ui.setElementClasses("feedback-toast", "toast bad");
        }

        ui.setElementVisibility("feedback-toast", true);
        ui.setElementVisibility("next-button", true);
    }

    @ClickListener(id="next-button")
    public void onNextButtonClicked(UI<AppState> ui) {
        currentQuestionIndex += 1;
        this.loadQuestion(ui, this.questionsToAsk[this.currentQuestionIndex]);
    }
}
