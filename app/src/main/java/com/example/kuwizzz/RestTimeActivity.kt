package com.example.kuwizzz

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RestTimeActivity : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var stopButton: Button
    private var countDownTimer: CountDownTimer? = null

    private var studyTime: Int = 0
    private var restTime: Int = 0
    private var currentQuestionIndex: Int = 0
    private var questionsList: ArrayList<Question> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest_time)

        timerTextView = findViewById(R.id.restTimerTextView)
        stopButton = findViewById(R.id.stopButton)

        // Retrieve data from the intent
        studyTime = intent.getIntExtra("studyTime", 5)
        restTime = intent.getIntExtra("restTime", 1)
        currentQuestionIndex = intent.getIntExtra("questionIndex", 0)
        questionsList = intent.getSerializableExtra("questions") as? ArrayList<Question> ?: arrayListOf()

        // Validate data
        if (questionsList.isEmpty()) {
            Toast.makeText(this, "No questions available. Returning to quiz setup.", Toast.LENGTH_SHORT).show()
            goToGenerateQuiz()
            return
        }

        startRestTimer(restTime)

        stopButton.setOnClickListener {
            showEndOptionsDialog()
        }
    }

    private fun startRestTimer(timeInMinutes: Int) {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(timeInMinutes * 60 * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutesRemaining = (millisUntilFinished / 1000) / 60
                val secondsRemaining = (millisUntilFinished / 1000) % 60
                timerTextView.text = String.format("Rest Time Remaining: %d:%02d", minutesRemaining, secondsRemaining)
            }

            override fun onFinish() {
                Toast.makeText(this@RestTimeActivity, "Rest time is over! Time to study again!", Toast.LENGTH_SHORT).show()
                returnToQuiz()
            }
        }.start()
    }

    private fun showEndOptionsDialog() {
        countDownTimer?.cancel()

        AlertDialog.Builder(this)
            .setTitle("Choose Option")
            .setMessage("Would you like to continue the quiz, create another quiz, or rest again?")
            .setPositiveButton("Continue Quiz") { _, _ -> returnToQuiz() }
            .setNegativeButton("Create Another Quiz") { _, _ -> goToGenerateQuiz() }
            .setNeutralButton("Rest Again") { _, _ -> restartRestTimer() }
            .setOnCancelListener { restartRestTimer() } // Restart timer if the dialog is dismissed
            .show()
    }

    private fun returnToQuiz() {
        if (currentQuestionIndex >= questionsList.size) {
            Toast.makeText(this, "Quiz is already complete. Creating a new quiz.", Toast.LENGTH_SHORT).show()
            goToGenerateQuiz()
            return
        }

        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra("studyTime", studyTime)
            putExtra("restTime", restTime)
            putExtra("questionIndex", currentQuestionIndex)
            putExtra("questions", questionsList)
        }
        startActivity(intent)
        finish()
    }

    private fun goToGenerateQuiz() {
        val intent = Intent(this, GenerateStudyGuideActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun restartRestTimer() {
        startRestTimer(restTime)
    }

    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel() // Pause the timer when the activity is paused
    }

    override fun onResume() {
        super.onResume()
        if (countDownTimer == null) {
            startRestTimer(restTime) // Restart the timer if it was canceled
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel() // Clean up the timer to avoid memory leaks
    }
}