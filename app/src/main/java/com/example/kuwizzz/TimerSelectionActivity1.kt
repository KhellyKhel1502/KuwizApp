package com.example.kuwizzz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TimerSelectionActivity1 : AppCompatActivity() {

    private lateinit var studyTimeEditText: EditText
    private lateinit var restTimeEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_selection1)

        studyTimeEditText = findViewById(R.id.studyTimeEditText)
        restTimeEditText = findViewById(R.id.restTimeEditText)

        val nextButton: Button = findViewById(R.id.startQuizButton)
        nextButton.setOnClickListener {
            val studyTimeInput = studyTimeEditText.text.toString()
            val restTimeInput = restTimeEditText.text.toString()

            if (studyTimeInput.isEmpty() || restTimeInput.isEmpty()) {
                Toast.makeText(this, "Please enter both study and rest times.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val studyTimeInMinutes = studyTimeInput.toInt()
            val restTimeInMinutes = restTimeInput.toInt()
            val questions = intent.getSerializableExtra("questions") as? ArrayList<Question>

            if (questions.isNullOrEmpty()) {
                Toast.makeText(this, "No questions received. Please go back and add questions.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtra("studyTime", studyTimeInMinutes)
                putExtra("restTime", restTimeInMinutes)
                putExtra("questions", questions)
            }
            startActivity(intent)
        }
    }
}
