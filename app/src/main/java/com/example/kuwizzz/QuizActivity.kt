package com.example.kuwizzz

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var choiceAButton: RadioButton
    private lateinit var choiceBButton: RadioButton
    private lateinit var choiceCButton: RadioButton
    private lateinit var nextQuestionButton: Button
    private lateinit var finishQuizButton: Button
    private lateinit var timerText: TextView

    private var questionsList: ArrayList<Question> = arrayListOf()
    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0
    private var wrongAnswersCount = 0
    private var userAnswers: ArrayList<String> = arrayListOf()

    private var studyTime: Int = 0
    private var restTime: Int = 0

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actual_quiz)

        questionText = findViewById(R.id.questionText)
        choiceAButton = findViewById(R.id.choiceAButton)
        choiceBButton = findViewById(R.id.choiceBButton)
        choiceCButton = findViewById(R.id.choiceCButton)
        nextQuestionButton = findViewById(R.id.nextQuestionButton)
        finishQuizButton = findViewById(R.id.finishQuizButton)
        timerText = findViewById(R.id.timerText)

        studyTime = intent.getIntExtra("studyTime", 5)
        restTime = intent.getIntExtra("restTime", 1)
        currentQuestionIndex = intent.getIntExtra("questionIndex", 0)
        questionsList = intent.getSerializableExtra("questions") as ArrayList<Question>

        showQuestion(currentQuestionIndex)
        startStudyTimer(studyTime)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupChoices)
        radioGroup.setOnCheckedChangeListener { _, _ ->
            nextQuestionButton.isEnabled = true
        }

        nextQuestionButton.setOnClickListener {
            val selectedAnswer = when (radioGroup.checkedRadioButtonId) {
                R.id.choiceAButton -> "A"
                R.id.choiceBButton -> "B"
                R.id.choiceCButton -> "C"
                else -> return@setOnClickListener
            }
            userAnswers.add(selectedAnswer)

            val currentQuestion = questionsList[currentQuestionIndex]
            if (selectedAnswer == currentQuestion.correctAnswer) {
                correctAnswersCount++
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                wrongAnswersCount++
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            }

            currentQuestionIndex++
            if (currentQuestionIndex < questionsList.size) {
                showQuestion(currentQuestionIndex)
                radioGroup.clearCheck()
                nextQuestionButton.isEnabled = false
            } else {
                finishQuiz()
            }
        }

        finishQuizButton.setOnClickListener { finishQuiz() }
    }

    private fun startStudyTimer(timeInMinutes: Int) {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(timeInMinutes * 60 * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutesRemaining = (millisUntilFinished / 1000) / 60
                val secondsRemaining = (millisUntilFinished / 1000) % 60
                timerText.text = String.format("Study Time Remaining: %d:%02d", minutesRemaining, secondsRemaining)
            }

            override fun onFinish() {
                Toast.makeText(this@QuizActivity, "Study time is over! Time to rest!", Toast.LENGTH_SHORT).show()
                goToRestTime()
            }
        }.start()
    }

    private fun showQuestion(index: Int) {
        val question = questionsList[index]
        questionText.text = question.questionText
        choiceAButton.text = question.choiceA
        choiceBButton.text = question.choiceB
        choiceCButton.text = question.choiceC
    }

    private fun goToRestTime() {
        val intent = Intent(this, RestTimeActivity::class.java).apply {
            putExtra("studyTime", studyTime)
            putExtra("restTime", restTime)
            putExtra("questionIndex", currentQuestionIndex)
            putExtra("questions", questionsList)
        }
        startActivity(intent)
        finish()
    }

    private fun finishQuiz() {
        val intent = Intent(this, UserProgressActivity::class.java).apply {
            putExtra("questionsList", questionsList)
            putStringArrayListExtra("userAnswers", userAnswers)
            putExtra("correctAnswersCount", correctAnswersCount)
            putExtra("wrongAnswersCount", wrongAnswersCount)
        }
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}