package com.example.kuwizzz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val emailField = findViewById<EditText>(R.id.emailField)
        val passwordField = findViewById<EditText>(R.id.passwordField)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        val signUp = findViewById<TextView>(R.id.signUp)


        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            // Placeholder login logic: check if email and password are not empty
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Save login status in SharedPreferences
                val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                // Navigate to HomepageActivity
                val intent = Intent(this, HomepageActivity::class.java)
                startActivity(intent)
                finish() // Close LoginActivity to prevent going back to it
            } else {
                // Show error if fields are empty
                Toast.makeText(this, "Please enter your email and password", Toast.LENGTH_SHORT).show()
            }
        }


        forgotPassword.setOnClickListener {

        }


        signUp.setOnClickListener {

        }
    }
}
