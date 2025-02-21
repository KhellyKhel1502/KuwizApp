package com.example.kuwizzz

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class FlashcardQuizActivity : AppCompatActivity() {

    private var flashcards = mutableListOf<Flashcard>()
    private var currentIndex = 0
    private var studyTime: Long = 0
    private var score: Int = 0
    private lateinit var timerTextView: TextView
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard_quiz)

        // kunin yung oras sa timerselection chucchu
        studyTime = intent.getIntExtra("studyTime", 0).toLong() * 60 * 1000
        timerTextView = findViewById(R.id.timerTextView)

        setupFlashcards()
        showFlashcard(currentIndex)
        startTimer(studyTime) // Start the timer with the initial study time
    }

    private fun setupFlashcards() {
        // Add flashcards to the list
        flashcards.add(Flashcard("What is the main programming language used for Android development?", "Kotlin", "Java", "Kotlin"))
        flashcards.add(Flashcard("Which file is used for app configuration in Android?", "AndroidManifest.xml", "build.gradle", "AndroidManifest.xml"))
        flashcards.add(Flashcard("What is the layout file extension in Android?", ".xml", ".layout", ".xml"))
        flashcards.add(Flashcard("Which method is called when an Android activity is created?", "onCreate()", "init()", "onCreate()"))
        flashcards.add(Flashcard("What is the default directory for storing resources?", "res/", "src/", "res/"))
        flashcards.add(Flashcard("Which component is used to display a list of items in Android?", "RecyclerView", "ListView", "RecyclerView"))
        flashcards.add(Flashcard("What is the purpose of Gradle in Android Studio?", "Build automation", "Database management", "Build automation"))
        flashcards.add(Flashcard("Which Android component is used to handle user interactions?", "Activity", "Service", "Activity"))
        flashcards.add(Flashcard("What is the command to run the Android emulator from the command line?", "emulator -avd <AVD_Name>", "adb start", "emulator -avd <AVD_Name>"))
        flashcards.add(Flashcard("What is the purpose of Android Debug Bridge (ADB)?", "Communication with devices", "UI testing", "Communication with devices"))

        flashcards.shuffle() // Shuffle the flashcards for variety
    }

    private fun showFlashcard(index: Int) {
        val flashcard = flashcards[index]
        findViewById<TextView>(R.id.questionTextView).text = flashcard.question
        findViewById<Button>(R.id.option1Button).text = flashcard.option1
        findViewById<Button>(R.id.option2Button).text = flashcard.option2

        // Set click listeners for answer options
        findViewById<Button>(R.id.option1Button).setOnClickListener {
            checkAnswer(flashcard.option1, flashcard.correctAnswer)
        }
        findViewById<Button>(R.id.option2Button).setOnClickListener {
            checkAnswer(flashcard.option2, flashcard.correctAnswer)
        }
    }

    private fun startTimer(timeInMillis: Long) {
        // Cancel any existing timer
        countDownTimer?.cancel()

        timeRemaining = timeInMillis // Set the time remaining to the provided time
        countDownTimer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished // Update remaining time
                val minutesRemaining = (millisUntilFinished / 1000) / 60
                val secondsRemaining = (millisUntilFinished / 1000) % 60
                timerTextView.text = String.format("Time remaining: %d:%02d", minutesRemaining, secondsRemaining)
            }

            override fun onFinish() {
                Toast.makeText(this@FlashcardQuizActivity, "Time's up!", Toast.LENGTH_SHORT).show()
                showEndOptionsDialog()
            }
        }.start()
    }

    private fun checkAnswer(selectedOption: String, correctAnswer: String) {
        if (selectedOption == correctAnswer) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            score++
        } else {
            Toast.makeText(this, "Wrong! The correct answer was: $correctAnswer", Toast.LENGTH_SHORT).show()
        }

        if (currentIndex < flashcards.size - 1) {
            currentIndex++
            showFlashcard(currentIndex)
        } else {
            Toast.makeText(this, "Quiz Finished!", Toast.LENGTH_LONG).show()
            showEndOptionsDialog()
        }
    }

    private fun showEndOptionsDialog() {
        countDownTimer?.cancel() // Stop the timer when quiz ends

        AlertDialog.Builder(this)
            .setTitle("Study Time Finished!")
            .setMessage("Your Score: $score/${flashcards.size}\nWould you like to take the quiz again or take a break?")
            .setPositiveButton("Take Quiz Again") { _, _ ->
                currentIndex = 0
                score = 0
                showFlashcard(currentIndex)
                startTimer(timeRemaining) // Restart with the remaining time
            }
            .setNegativeButton("Take a Break") { _, _ ->
                val restTime = intent.getIntExtra("restTime", 5)
                val intent = Intent(this, RestTimeActivity::class.java)
                intent.putExtra("restTime", restTime)
                startActivity(intent)
                finish()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel() // Cancel the timer if the activity is destroyed
    }
}

// Define the Flashcard data class
data class Flashcard(val question: String, val option1: String, val option2: String, val correctAnswer: String)
