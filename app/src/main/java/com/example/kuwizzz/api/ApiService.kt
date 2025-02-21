package com.example.kuwizzz.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Define the API service interface
interface ApiService {
    @POST("generate-questions") // Ensure this endpoint matches your Flask API
    fun generateQuestions(@Body request: QuestionRequest): Call<List<QuestionResponse>> // Update return type
}

// Define the data class for the request body
data class QuestionRequest(val text: String)
