package com.example.kuwizzz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserProgressActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var scoreTextView: TextView
    private lateinit var retakeQuizButton: Button
    private lateinit var homeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_progress)

        recyclerView = findViewById(R.id.recyclerView)
        scoreTextView = findViewById(R.id.scoreTextView)
        retakeQuizButton = findViewById(R.id.retakeQuizButton)
        homeButton = findViewById(R.id.homeButton)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val questionsList = intent.getSerializableExtra("questionsList") as? ArrayList<Question>
        val userAnswers = intent.getStringArrayListExtra("userAnswers")
        val correctAnswers = intent.getIntExtra("correctAnswersCount", 0)
        val wrongAnswers = intent.getIntExtra("wrongAnswersCount", 0)

        scoreTextView.text = "Correct: $correctAnswers\nWrong: $wrongAnswers"

        if (questionsList != null && userAnswers != null) {
            recyclerView.adapter = UserProgressAdapter(questionsList, userAnswers)
        }

        retakeQuizButton.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
            finish()
        }

        homeButton.setOnClickListener {
            startActivity(Intent(this, HomepageActivity::class.java))
            finish()
        }
    }
}
