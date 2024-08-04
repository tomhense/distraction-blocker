package com.example.distractionblocker

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LockScreenActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_screen)

        val passwordInput = findViewById<EditText>(R.id.password_input)
        val unlockButton = findViewById<Button>(R.id.unlock_button)

        unlockButton.setOnClickListener {
            val password = passwordInput.text.toString()
            if (validatePassword(password)) {
                finish()
            } else {
                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validatePassword(password: String): Boolean {
        // Add your password validation logic here
        // For simplicity, let's use a hardcoded password
        return password == "1234"
    }

    override fun onBackPressed() {
        // Prevent the user from closing the lock screen
    }
}