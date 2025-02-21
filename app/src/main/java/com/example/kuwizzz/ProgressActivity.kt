package com.example.kuwizzz

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        val questionsList = intent.getSerializableExtra("questions") as ArrayList<Question>
        val correctAnswersCount = intent.getIntExtra("correctAnswersCount", 0)
        val wrongAnswersCount = intent.getIntExtra("wrongAnswersCount", 0)

        val progressTextView: TextView = findViewById(R.id.progressTextView)
        val questionDetailsTextView: TextView = findViewById(R.id.questionDetailsTextView)

        progressTextView.text = "Correct Answers: $correctAnswersCount\nWrong Answers: $wrongAnswersCount"

        val details = StringBuilder()
        for (question in questionsList) {
            details.append("${question.questionText}\n")
            details.append("Correct: ${question.correctAnswer}\n")

        }

        questionDetailsTextView.text = details.toString()
    }
}
