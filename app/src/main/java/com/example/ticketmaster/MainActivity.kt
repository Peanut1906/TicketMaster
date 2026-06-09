package com.example.ticketmaster

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ticketmaster.activities.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        startActivity(
            Intent(
                this,
                LoginActivity::class.java
            )
        )

        finish()
    }
}