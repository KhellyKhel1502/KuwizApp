package com.example.kuwizzz

import java.io.Serializable

data class Question(
    val questionText: String,
    val choiceA: String,
    val choiceB: String,
    val choiceC: String,
    val correctAnswer: String,
) : Serializable {

    // Validate if the correct answer is one of the provided choices
    init {
        require(correctAnswer in listOf("A", "B", "C")) {
            "Correct answer must be one of A, B, or C"
        }
    }

    // Return all choices in a list for easier access
    fun getChoices(): List<String> {
        return listOf(choiceA, choiceB, choiceC)
    }

    // Override the toString method for better debugging or display
    override fun toString(): String {
        return "com.example.kuwizzz.Question: $questionText\nA: $choiceA\nB: $choiceB\nC: $choiceC\nCorrect Answer: $correctAnswer"
    }
}