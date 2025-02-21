package com.example.kuwizzz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AddQuestionsActivity : AppCompatActivity() {
    private lateinit var addQuestionButton: Button
    private lateinit var finishButton: Button
    private var questionCount = 0 // To track the number of questions added
    private val questions = mutableListOf<Question>() // List to hold questions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_input_page)

        // Initialize buttons
        addQuestionButton = findViewById(R.id.buttonAddQuestion)
        finishButton = findViewById(R.id.buttonFinishQuiz)

        // Set listeners for buttons
        addQuestionButton.setOnClickListener {
            addQuestion()
        }

        finishButton.setOnClickListener {
            finishQuiz()
        }

        // Initially disable the finish button
        finishButton.isEnabled = false
    }

    private fun addQuestion() {
        // Fetch data from input fields
        val questionText = findViewById<EditText>(R.id.editTextQuestion1).text.toString()
        val choiceA = findViewById<EditText>(R.id.editTextChoiceA1).text.toString()
        val choiceB = findViewById<EditText>(R.id.editTextChoiceB1).text.toString()
        val choiceC = findViewById<EditText>(R.id.editTextChoiceC1).text.toString()

        // Get selected correct answer from RadioGroup
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupCorrectAnswer)
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId
        val correctAnswer = when (selectedRadioButtonId) {
            R.id.radioButtonA -> "A"
            R.id.radioButtonB -> "B"
            R.id.radioButtonC -> "C"
            else -> {
                Toast.makeText(this, "Please select a correct answer", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Validate inputs
        if (questionText.isBlank() || choiceA.isBlank() || choiceB.isBlank()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Check for duplicate questions
        if (questions.any { it.questionText == questionText }) {
            Toast.makeText(this, "This question has already been added", Toast.LENGTH_SHORT).show()
            return
        }

        // Add the question to the list
        val newQuestion = Question(questionText, choiceA, choiceB, choiceC, correctAnswer)
        questions.add(newQuestion)

        // After adding the question, increase the counter
        questionCount++

        // Update the question number in the TextView
        findViewById<TextView>(R.id.textViewQuestion1).text = "Question ${questionCount + 1}"

        // If 3 or more questions are added, enable the finish button
        if (questionCount >= 3) {
            finishButton.isEnabled = true
        }

        // Provide feedback to the user
        Toast.makeText(this, "Question added successfully!", Toast.LENGTH_SHORT).show()

        // Optionally, reset fields for the next input
        resetFields()
    }

    private fun resetFields() {
        // Reset input fields for the next question
        findViewById<EditText>(R.id.editTextQuestion1).text.clear()
        findViewById<EditText>(R.id.editTextChoiceA1).text.clear()
        findViewById<EditText>(R.id.editTextChoiceB1).text.clear()
        findViewById<EditText>(R.id.editTextChoiceC1).text.clear()

        // Reset the RadioGroup
        findViewById<RadioGroup>(R.id.radioGroupCorrectAnswer).clearCheck()
    }

    private fun finishQuiz() {
        // Check if less than 3 questions are added
        if (questions.size < 3) {
            // Show an alert dialog if less than 3 questions are added
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Please input at least 3 questions to finish the quiz.")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            alertDialog.show()
        } else {
            // Proceed with finishing the quiz
            val intent = Intent(this, TimerSelectionActivity1::class.java)
            intent.putExtra("questions", ArrayList(questions)) // Pass questions as a serializable extra
            startActivity(intent)

            // Show a toast or message
            Toast.makeText(this, "Quiz Finished! Now set your study and rest times.", Toast.LENGTH_SHORT).show()
        }
    }
}