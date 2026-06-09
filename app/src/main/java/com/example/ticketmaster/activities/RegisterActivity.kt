package com.example.ticketmaster.activities
import com.example.ticketmaster.R

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ticketmaster.model.User
import com.example.ticketmaster.util.DatabaseProvider
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameField =
            findViewById<EditText>(R.id.editUsername)

        val passwordField =
            findViewById<EditText>(R.id.editPassword)

        val registerButton =
            findViewById<Button>(R.id.buttonRegister)

        val db =
            DatabaseProvider.getDatabase(this)

        registerButton.setOnClickListener {

            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            thread {

                val existingUser =
                    db.userDao().getUser(username)

                if (existingUser != null) {

                    runOnUiThread {
                        Toast.makeText(
                            this,
                            "User already exists",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    return@thread
                }

                db.userDao().insert(
                    User(
                        username = username,
                        password = password
                    )
                )

                runOnUiThread {

                    Toast.makeText(
                        this,
                        "Account created successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }
        }
    }
}