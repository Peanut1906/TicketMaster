package com.example.ticketmaster.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ticketmaster.R
import com.example.ticketmaster.util.DatabaseProvider
import com.example.ticketmaster.util.SessionManager
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameField =
            findViewById<EditText>(R.id.editUsername)

        val passwordField =
            findViewById<EditText>(R.id.editPassword)

        val loginButton =
            findViewById<Button>(R.id.buttonLogin)

        val registerButton =
            findViewById<Button>(R.id.buttonRegister)

        val db =
            DatabaseProvider.getDatabase(this)

        registerButton.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }

        loginButton.setOnClickListener {

            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            if (username.isBlank() || password.isBlank()) {

                Toast.makeText(
                    this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            thread {

                val user =
                    db.userDao().login(
                        username,
                        password
                    )

                runOnUiThread {

                    if (user != null) {

                        SessionManager.saveUser(
                            this,
                            username
                        )

                        startActivity(
                            Intent(
                                this,
                                DashboardActivity::class.java
                            )
                        )

                        finish()

                    } else {

                        Toast.makeText(
                            this,
                            "Invalid credentials",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}