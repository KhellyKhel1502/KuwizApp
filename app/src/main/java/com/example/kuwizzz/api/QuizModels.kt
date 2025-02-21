package com.example.kuwizzz.api

// Data class for the request body sent to the API
data class QuizRequest(val content: String)

// Data class for the response body received from the API
data class QuizResponse(val questions: List<Question>)

// Data class representing a single question (you may need to adjust based on your API's response)
data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)
