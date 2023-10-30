package com.cd.quizwhiz.Questions;

/**
 * The `Question` class represents a quiz question with the text of the question,
 * answer options, the correct answer, category, and difficulty level.
 */
public class Question {
    // Declare instance variables for the question, answer options, actual answer, and category.
    String Question;       // The text of the question
    String[] Answers;      // An array of answer options
    int ActualAnswer;      // The index of the correct answer
    Category category;     // The category of the question
    Difficulty difficulty; // The Difficulty of the question

    /**
     * Initializes a new question with the provided details.
     *
     * @param question The text of the question.
     * @param answers An array of answer options.
     * @param actualAnswer The index of the correct answer in the 'answers' array.
     * @param category The category of the question.
     * @param difficulty The difficulty level of the question.
     */
    public Question(String question, String[] answers, int actualAnswer, Category category, Difficulty difficulty) {
        this.Question = question;
        this.Answers = answers;
        this.ActualAnswer = actualAnswer;
        this.category = category;
        this.difficulty = difficulty;
    }

    /**
     * Compares the provided answer index with the correct answer index.
     *
     * @param Answer The answer index to compare with the correct answer.
     * @return `true` if the provided answer index is the correct answer, `false` otherwise.
     */
    boolean CompareAnswer(int Answer) {
        return Answer == this.ActualAnswer;
    }

    /**
     * Gets the text of the question.
     *
     * @return The text of the question.
     */
    public String getQuestion() {
        return Question;
    }

    /**
     * Gets an array of answer options.
     *
     * @return An array of answer options.
     */
    public String[] getAnswers() {
        return Answers;
    }

    /**
     * Gets the index of the correct answer in the 'answers' array.
     *
     * @return The index of the correct answer.
     */
    public int getActualAnswer() {
        return ActualAnswer;
    }

    /**
     * Gets the category of the question.
     *
     * @return The category of the question.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Gets the difficulty level of the question.
     *
     * @return The difficulty level of the question.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }
}
