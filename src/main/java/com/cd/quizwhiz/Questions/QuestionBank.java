package com.cd.quizwhiz.Questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 * A class that represents a question bank containing various categories of questions
 * with different levels of difficulty.
 */

public class QuestionBank {
    public static final Question[] QUESTIONS = {
            // DISCRETE MATHS
            //novice
            new Question("In computer science, what is the binary number system based on?\n", new String[]
                    {"Base2", "Base10", "Base8", "Base32"}, 0, Category.DiscreteMaths, Difficulty.novice),

            new Question("Which logical operator returns true if both of its operands are true?", new String[]
                    {"AND", "OR", "NOT", "XOR"}, 0, Category.DiscreteMaths, Difficulty.novice),
            //intermediate
            new Question("What is the purpose of Big O notation in computer science?\n", new String[]{
                    " To denote the largest number in a dataset\n",
                    "To analyze the efficiency of algorithms\n",
                    "To represent binary numbers in a compact form",
                    "To categorize data types"}, 1, Category.DiscreteMaths, Difficulty.intermediate),

            new Question("Surjectivity means that ...?", new String[]{
                    "... every element in the codomain has at most one element in the domain that maps to it",
                    "... every element in codomain has exactly one element in the domain that maps to it",
                    "... every element in the codomain has at least one element in the domain that maps to it",
                    "... every element in the domain maps onto at most one element in the codomain"
            }, 2, Category.DiscreteMaths, Difficulty.intermediate),
            //expert
            new Question("Under which of the following conditions is the inverse of a matrix NOT defined?", new String[]{
                    "If it is an identity matrix",
                    "The determinant is negative",
                    "The sum of its elements is zero",
                    "The determinant is zero"
            }, 3, Category.DiscreteMaths, Difficulty.expert),

            new Question("What is the maximum number of walks of length 3 that may be present on a graph composed of 5 nodes?", new String[]{
                    "5",
                    "15",
                    "25",
                    "125"
            }, 2, Category.DiscreteMaths, Difficulty.expert),

            // COMPUTER SCIENCE
            //novice
            new Question("Backus-Naur Form describes the ___ of a language", new String[]{
                    "semantics",
                    "syntax",
                    "noun-verb order",
                    "complexity"
            }, 1, Category.ComputerSci, Difficulty.novice),

            new Question("A lattice contained entirely within another lattice is called a:", new String[]{
                    "sublattice",
                    "semilattice",
                    "sup-semilattice",
                    "order homomorphism"
            }, 0, Category.ComputerSci, Difficulty.novice),
            //intermediate
            new Question("A Binary Decision Diagram is a special kind of:", new String[]{
                    "weighted acyclic graph",
                    "directed acyclic graph",
                    "weighted directed graph",
                    "undirected weighted graph"
            }, 1, Category.ComputerSci, Difficulty.intermediate),

            new Question("Which of the following is NOT a step in forming an ROBDD from a binary tree?", new String[]{
                    "merging isomorphic decision nodes",
                    "substituting equivalent variables",
                    "removing duplicate terminal nodes",
                    "eliminating redundant tests"
            }, 1, Category.ComputerSci, Difficulty.intermediate),
            //expert
            new Question("If a relation is reflexive, symmetric, and transitive, it is:", new String[]{
                    "a partial order",
                    "a total order",
                    "an equivilance relation",
                    "an empty relation"
            }, 2, Category.ComputerSci, Difficulty.expert),

            new Question("which of the following partial orders is NOT a lattice?", new String[]{
                    "(ℕ, |)",
                    "(ℕ - {1}, |)",
                    "(ℤ, ≤)",
                    "(P(ℕ), ⊆)"
            }, 1, Category.ComputerSci, Difficulty.expert),
            //COMP ORG
            //novice
            new Question("What does CPU stand for?", new String[]{
                    "Computer Personal Unit", "Central Proccess Unidiagram",
                    "Centralized Program Unit",
                    "Central Proccesing Unit"
            }, 3, Category.ComputerOrg, Difficulty.novice),

            new Question("Which component of a computer stores data long-term?", new String[]{
                    "RAM",
                    "CPU",
                    "Hard Drive",
                    "GPU"
            }, 2, Category.ComputerOrg, Difficulty.novice),
            //intermediate
            new Question("What does the acronym GPU stand for?", new String[]{
                    "General Processing Unit",
                    "Graphics Performace Unit",
                    "Graphical Proccesing Unit",
                    "General Purpose Unit"
            }, 2, Category.ComputerOrg, Difficulty.intermediate),
            new Question("What is the purpose of a motherboard in a computer?", new String[]{
                    "To display images on the screen"
                    ,"To process data"
                    ,"To connect and communicate with hardware componenets",
                    "To store files and documents"
            }, 2, Category.ComputerOrg, Difficulty.intermediate),
            //expert
            new Question("What is pipelining in computer architecture", new String[]{
                    "A technique used in parallel computing to process multiple tasks simultaneously",
                    "A method for cooling computer components",
                    "A form of memory storage",
                    "A technique for creating computer viruses"
            }, 0, Category.ComputerOrg, Difficulty.expert),

            new Question("To reduce the memory access time we generally make use of ______", new String[]{
                    "SDRAM's",
                    "Headps",
                    "Cache's",
                    "Higher capacity RAM's"
            }, 2, Category.ComputerOrg, Difficulty.expert)
    };

    //return a randomly sorted list of all questions
    public static Question[] Coop(){
        /**
         * Retrieves a randomly sorted list of 12 questions in the question bank.
         * @return An array of 12 Question objects containing randomized questions.
         */
        // Create an ArrayList to store the questions
        ArrayList<Question> questionList = new ArrayList<>();

        // Initialize a random variable
        Random random = new Random();

        // Iterate through your QUESTIONS array
        Collections.addAll(questionList, QUESTIONS);

        // Shuffle the ArrayList to randomize the questions
        shuffleArrayList(questionList, random);


        // Convert the ArrayList back to an array// subList gets a sublist of the array of size 12 questions as we want
        //6 questions for player1 and 6 questions for player 2
        Question[] questions = questionList.subList(0, 12).toArray(new Question[12]);  /// changed from sub array(0,11)
        // to (0,12) as it was only returning 11

        // Return the array of randomized questions
        return questions;
    }

    public static Question[] IncDifficulty(Category category) {
        /**
         * Retrieves questions of a specified category and returns them in the original order.
         * @param category The category of questions to retrieve.
         * @return An array of Question objects with the specified category.
         */
         List<Question>questionList = new ArrayList<>();
        //for each question in Questions array
        for (Question Q : QUESTIONS) {
            //if the question category = required category then add it to our question Array
            if (Q.category == category) {
                questionList.add(Q);
            }
        }

        // Convert the list to an array
        Question[] questions = questionList.toArray(new Question[questionList.size()]);
        //returns our list of questions
        return questions;
    }



    public static Question[] RandomQuestion(Category category)
    {
        /**
         * Retrieves questions of a specified category and returns them in a randomized order.
         * @param category The category of questions to retrieve.
         * @return An array of Question objects with the specified category, randomized.
         */
        // Create an ArrayList to store the questions
        ArrayList<Question> questionList = new ArrayList<>();

        // Initialize a random variable
        Random random = new Random();

        // Iterate through your QUESTIONS array
        for (Question Q : QUESTIONS)
        {
            // Check if the question category matches the specified category
            if (Q.category == category)
            {
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

    public static void shuffleArrayList(ArrayList<Question> list, Random random)
    {
        /**
         * Shuffles an ArrayList of questions using the Fisher-Yates shuffle algorithm.
         * @param list The ArrayList to be shuffled.
         * @param random A random number generator to introduce randomness into the shuffle.
         */
        //i used fisherYatesShuffle to get a good random shuffle
        int n = list.size();

        for (int i = n - 1; i > 0; i--)//iterates from the list size to 0
        {
            int j = random.nextInt(i + 1);//gets a random int between 0 and i+1

            // Swap elements at indices i and j
            Question temp = list.get(i);//store i`s value ina temp variable
            list.set(i, list.get(j));// set i = j
            list.set(j, temp);// set j = i
        }
    }
}


