package com.cd.quizwhiz.Questions;
public class Question {
    // Declare instance variables for the question, answer options, actual answer, and category.
    String Question;       // The text of the question
    String[] Answers;      // An array of answer options
    int ActualAnswer;      // The index of the correct answer
    Category category;     // The category of the question
    Difficulty difficulty; // The Difficulty of the question

    // Constructor to initialize the question object.
    public Question(String question, String[] answers, int actualAnswer, Category category,Difficulty difficulty) {
        this.Question = question;
        this.Answers = answers;
        this.ActualAnswer = actualAnswer;
        this.category = category;
        this.difficulty = difficulty;
    }

    // Compare the provided answer with the correct answer.
    boolean CompareAnswer(int Answer) {
        return Answer == this.ActualAnswer;
    }
}