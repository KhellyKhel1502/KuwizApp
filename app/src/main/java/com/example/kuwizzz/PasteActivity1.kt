package com.example.kuwizzz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class PasteTextActivity1 : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var generateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paste_text1)

        editText = findViewById(R.id.editText)
        generateButton = findViewById(R.id.generateButton)

        generateButton.setOnClickListener {
            val pastedText = editText.text.toString()
            val studyTime = intent.getIntExtra("studyTime", 0)
            val restTime = intent.getIntExtra("restTime", 0)

            // Pass the text and timers to the next activity
            val intent = Intent(this, TimerSelectionActivity1::class.java)
            intent.putExtra("pastedText", pastedText)
            intent.putExtra("studyTime", studyTime)
            intent.putExtra("restTime", restTime)
            startActivity(intent)
        }
    }
}
