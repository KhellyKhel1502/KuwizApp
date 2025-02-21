package com.example.kuwizzz

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GenerateStudyGuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generatestudyguide)

        // Back and settings buttons
        val backButton = findViewById<ImageView>(R.id.backButton)
        val settingsButton = findViewById<ImageView>(R.id.settingsButton)

        // Action buttons
        val pasteTextButton = findViewById<LinearLayout>(R.id.pasteTextButton)
        val selectFileButton = findViewById<LinearLayout>(R.id.selectFileButton)

        // Click listeners
        backButton.setOnClickListener {
            finish() // Close the current activity and return to the previous one
        }

        settingsButton.setOnClickListener {
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
            // Add settings functionality here
        }


        pasteTextButton.setOnClickListener {
            // Navigate to TimerSelectionActivity to paste text and set timers
            val intent = Intent(this, PasteTextActivity1::class.java)
            startActivity(intent)
        }

        selectFileButton.setOnClickListener {
            Toast.makeText(this, "Select File Clicked", Toast.LENGTH_SHORT).show()
            // Implement select file functionality
        }
    }
}
