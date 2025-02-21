package com.example.kuwizzz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomepageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Find the Create Quiz button by its ID (updated button)
        val createQuizButton = findViewById<Button>(R.id.createQuizButton)

        // Find the Set Reminder button by its ID
        val setReminderButton = findViewById<Button>(R.id.setReminderButton)


        // Set an OnClickListener for the Create Quiz button
        createQuizButton.setOnClickListener {
            // Create an intent to navigate to AddQuestionsActivity (for adding questions to the quiz)
            val intent = Intent(this, AddQuestionsActivity::class.java)
            startActivity(intent)
        }

        // Set an OnClickListener for the Set Reminder button
        setReminderButton.setOnClickListener {
            // Create an intent to navigate to SetReminderActivity
            val intent = Intent(this, SetReminderActivity::class.java)
            startActivity(intent)
        }
    }
}
