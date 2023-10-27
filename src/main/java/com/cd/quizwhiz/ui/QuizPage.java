package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.Questions.Question;
import com.cd.quizwhiz.uiframework.ClickListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;

public class QuizPage extends UIPage<AppState> {
    private final Question[] questionsToAsk;
    private int currentQuestionIndex = 0;
    private boolean answerLocked = false;
    protected UIPage<AppState> statsPage;

    public QuizPage(Question[] questionsToAsk) {
        super("quiz");
        
        this.questionsToAsk = questionsToAsk;
        this.statsPage = new StatsPage(true);
    }

    @Override
    public void onStart(UI<AppState> ui) {
        this.loadQuestion(ui, this.questionsToAsk[0]);

        for (int i = 0; i < 4; i++) {
            // Java has strict rules about lambdas capturing variables from another scope.
            // Technically, we define i in another scope, so we can't use it in the click listener lambda.
            // So, to get around that, we make a copy of i within this scope.
            int finalI = i;
            ui.addClickListener("answer-" + i, event -> {
                this.onAnswerClicked(ui, finalI);
            });
        }
    }

    private void loadQuestion(UI<AppState> ui, Question question) {
        this.answerLocked = false;
        ui.setElementText("question-text", question.getQuestion());

        for (int i = 0; i < 4; i++) {
            ui.setElementText("answer-" + i, question.getAnswers()[i]);
        }

        ui.setElementVisibility("feedback-toast", false);
        ui.setElementVisibility("next-button", false);
    }

    protected void onAnswerClicked(UI<AppState> ui, int id) {
        if (this.answerLocked)
            return;

        this.answerLocked = true;
        Question currentQuestion = this.questionsToAsk[this.currentQuestionIndex];

        if (id == currentQuestion.getActualAnswer()) {
            ui.setElementText("feedback-toast", "Correct!");
            ui.setElementClasses("feedback-toast", "toast good");
            incrementScore(ui);
        } else {
            ui.setElementText("feedback-toast", "Incorrect! The right answer was: " + currentQuestion.getAnswers()[currentQuestion.getActualAnswer()]);
            ui.setElementClasses("feedback-toast", "toast bad");
        }

        ui.setElementVisibility("feedback-toast", true);
        ui.setElementVisibility("next-button", true);
    }

    protected void incrementScore(UI<AppState> ui) {
        ui.getState().user.AddScore();
    }

    @ClickListener(id="next-button")
    public void onNextButtonClicked(UI<AppState> ui) {
        if (currentQuestionIndex == this.questionsToAsk.length - 1) {
            // We're done here!
            ui.loadPage(this.statsPage);
            return;
        }

        this.loadQuestion(ui, this.questionsToAsk[++this.currentQuestionIndex]);
    }
}
