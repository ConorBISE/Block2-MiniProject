package com.cd.quizwhiz.Questions;

public class QuestionBank {
    public static final Question[] QUESTIONS = {
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
    };
}
