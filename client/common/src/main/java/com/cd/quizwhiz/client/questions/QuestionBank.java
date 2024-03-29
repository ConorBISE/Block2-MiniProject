package com.cd.quizwhiz.client.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.cd.quizwhiz.common.questions.Category;
import com.cd.quizwhiz.common.questions.Difficulty;
import com.cd.quizwhiz.common.questions.Question;

/**
 * A class that represents a question bank containing various categories of
 * questions
 * with different levels of difficulty.
 */
public class QuestionBank {
        public static final Question[] QUESTIONS = {
                        // DISCRETE MATHS
                        // novice
                        new Question("In computer science, what is the binary number system based on?\n",
                                        new String[] { "Base 2", "Base 10", "Base 8", "Base 32" }, 0,
                                        Category.DISCRETE_MATHS, Difficulty.NOVICE),

                        new Question("Which logical operator returns true if both of its operands are true?",
                                        new String[] { "AND", "OR", "NOT", "XOR" }, 0, Category.DISCRETE_MATHS,
                                        Difficulty.NOVICE),

                        // intermediate
                        new Question("What is the purpose of Big O notation in computer science?\n", new String[] {
                                        "To denote the largest number in a dataset",
                                        "To analyse the efficiency of algorithms",
                                        "To represent binary numbers in a compact form",
                                        "To categorize data types" }, 1, Category.DISCRETE_MATHS,
                                        Difficulty.INTERMEDIATE),

                        new Question("Surjectivity means that ...?", new String[] {
                                        "... every element in the codomain has at most one element in the domain that maps to it",
                                        "... every element in codomain has exactly one element in the domain that maps to it",
                                        "... every element in the codomain has at least one element in the domain that maps to it",
                                        "... every element in the domain maps onto at most one element in the codomain"
                        }, 2, Category.DISCRETE_MATHS, Difficulty.INTERMEDIATE),

                        // expert
                        new Question("Under which of the following conditions is the inverse of a matrix NOT defined?",
                                        new String[] {
                                                        "If it is an identity matrix",
                                                        "The determinant is negative",
                                                        "The sum of its elements is zero",
                                                        "The determinant is zero"
                                        }, 3, Category.DISCRETE_MATHS, Difficulty.EXPERT),

                        new Question("What is the maximum number of walks of length 3 that may be present on a graph composed of 5 nodes?",
                                        new String[] {
                                                        "5",
                                                        "15",
                                                        "25",
                                                        "125"
                                        }, 2, Category.DISCRETE_MATHS, Difficulty.EXPERT),

                        // COMPUTER SCIENCE
                        // novice
                        new Question("Backus-Naur Form describes the ___ of a language", new String[] {
                                        "semantics",
                                        "syntax",
                                        "noun-verb order",
                                        "complexity"
                        }, 1, Category.COMPUTER_SCIENCE, Difficulty.NOVICE),

                        new Question("A lattice contained entirely within another lattice is called a:", new String[] {
                                        "sublattice",
                                        "semilattice",
                                        "sup-semilattice",
                                        "order homomorphism"
                        }, 0, Category.COMPUTER_SCIENCE, Difficulty.NOVICE),

                        // intermediate
                        new Question("A Binary Decision Diagram is a special kind of:", new String[] {
                                        "weighted acyclic graph",
                                        "directed acyclic graph",
                                        "weighted directed graph",
                                        "undirected weighted graph"
                        }, 1, Category.COMPUTER_SCIENCE, Difficulty.INTERMEDIATE),

                        new Question("Which of the following is NOT a step in forming an ROBDD from a binary tree?",
                                        new String[] {
                                                        "merging isomorphic decision nodes",
                                                        "substituting equivalent variables",
                                                        "removing duplicate terminal nodes",
                                                        "eliminating redundant tests"
                                        }, 1, Category.COMPUTER_SCIENCE, Difficulty.INTERMEDIATE),

                        // expert
                        new Question("If a relation is reflexive, symmetric, and transitive, it is:", new String[] {
                                        "a partial order",
                                        "a total order",
                                        "an equivilance relation",
                                        "an empty relation"
                        }, 2, Category.COMPUTER_SCIENCE, Difficulty.EXPERT),

                        new Question("which of the following partial orders is NOT a lattice?", new String[] {
                                        "(ℕ, |)",
                                        "(ℕ - {1}, |)",
                                        "(ℤ, ≤)",
                                        "(P(ℕ), ⊆)"
                        }, 1, Category.COMPUTER_SCIENCE, Difficulty.EXPERT),

                        // COMP ORG
                        // novice
                        new Question("What does CPU stand for?", new String[] {
                                        "Computer Personal Unit", "Central Process Unidiagram",
                                        "Centralized Program Unit",
                                        "Central Processing Unit"
                        }, 3, Category.COMPUTER_ORG, Difficulty.NOVICE),

                        new Question("Which component of a computer stores data long-term?", new String[] {
                                        "RAM",
                                        "CPU",
                                        "Hard Drive",
                                        "GPU"
                        }, 2, Category.COMPUTER_ORG, Difficulty.NOVICE),

                        // intermediate
                        new Question("What does the acronym GPU stand for?", new String[] {
                                        "General Processing Unit",
                                        "Graphics Performance Unit",
                                        "Graphical Processing Unit",
                                        "General Purpose Unit"
                        }, 2, Category.COMPUTER_ORG, Difficulty.INTERMEDIATE),
                        new Question("What is the purpose of a motherboard in a computer?", new String[] {
                                        "To display images on the screen",
                                        "To process data",
                                        "To connect and communicate with hardware componenets",
                                        "To store files and documents"
                        }, 2, Category.COMPUTER_ORG, Difficulty.INTERMEDIATE),

                        // expert
                        new Question("What is pipelining in computer architecture?", new String[] {
                                        "A technique used in parallel computing to process multiple tasks simultaneously",
                                        "A method for cooling computer components",
                                        "A form of memory storage",
                                        "A technique for creating computer viruses"
                        }, 0, Category.COMPUTER_ORG, Difficulty.EXPERT),

                        new Question("To reduce the memory access time we generally make use of ______", new String[] {
                                        "SDRAMs",
                                        "Heaps",
                                        "Caches",
                                        "Higher capacity RAMs"
                        }, 2, Category.COMPUTER_ORG, Difficulty.EXPERT)
        };

        // return a randomly sorted list of all questions
        /**
         * Retrieves a randomly sorted list of 12 questions in the question bank.
         * 
         * @return An array of 12 Question objects containing randomized questions.
         */
        public static Question[] headToHead() {

                // Create an ArrayList to store the questions
                ArrayList<Question> questionList = new ArrayList<>();

                // Initialize a random variable
                Random random = new Random();

                // Iterate through your QUESTIONS array
                Collections.addAll(questionList, QUESTIONS);

                // Shuffle the ArrayList to randomize the questions
                shuffleArrayList(questionList, random);

                // Convert the ArrayList back to an array
                Question[] questions = questionList.subList(0, 12).toArray(new Question[12]);

                // Return the array of randomized questions
                return questions;
        }

        /**
         * Retrieves questions of a specified category and returns them in the original
         * order.
         * 
         * @param category The category of questions to retrieve.
         * @return An array of Question objects with the specified category.
         */
        public static Question[] incDifficulty(Category category) {
                List<Question> questionList = new ArrayList<>();
                // for each question in Questions array
                for (Question Q : QUESTIONS) {
                        // if the question category = required category then add it to our question
                        // Array
                        if (Q.category == category) {
                                questionList.add(Q);
                        }
                }

                // Convert the list to an array
                Question[] questions = questionList.toArray(new Question[questionList.size()]);
                // returns our list of questions
                return questions;
        }

        /**
         * Retrieves questions of a specified category and returns them in a randomized
         * order.
         * 
         * @param category The category of questions to retrieve.
         * @return An array of Question objects with the specified category, randomized.
         */
        public static Question[] randomQuestion(Category category) {
                // Create an ArrayList to store the questions
                ArrayList<Question> questionList = new ArrayList<>();

                // Initialize a random variable
                Random random = new Random();

                // Iterate through your QUESTIONS array
                for (Question Q : QUESTIONS) {
                        // Check if the question category matches the specified category
                        if (Q.category == category) {
                                questionList.add(Q);
                        }
                }

                // Shuffle the ArrayList to randomize the questions
                shuffleArrayList(questionList, random);

                // Convert the ArrayList back to an array
                Question[] questions = questionList.toArray(new Question[questionList.size()]);

                // Return the array of randomized questions
                return questions;
        }

        /**
         * Shuffles an ArrayList of questions using the Fisher-Yates shuffle algorithm.
         * 
         * @param list   The ArrayList to be shuffled.
         * @param random A random number generator to introduce randomness into the
         *               shuffle.
         */
        public static void shuffleArrayList(ArrayList<Question> list, Random random) {
                int n = list.size();

                for (int i = n - 1; i > 0; i--) {
                        int j = random.nextInt(i + 1);

                        // Swap elements at indices i and j
                        Question temp = list.get(i);
                        list.set(i, list.get(j));
                        list.set(j, temp);
                }
        }
}
