package com.example.ticketmaster.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ticketmaster.R
import com.example.ticketmaster.model.Ticket
import com.example.ticketmaster.model.TicketPriority
import com.example.ticketmaster.model.TicketStatus
import com.example.ticketmaster.util.DatabaseProvider
import kotlin.concurrent.thread

class CreateTicketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_ticket)

        val titleField =
            findViewById<EditText>(R.id.editTitle)

        val descriptionField =
            findViewById<EditText>(R.id.editDescription)

        val userSpinner =
            findViewById<Spinner>(R.id.spinnerUsers)

        val statusSpinner =
            findViewById<Spinner>(R.id.spinnerStatus)

        val prioritySpinner =
            findViewById<Spinner>(R.id.spinnerPriority)

        val saveButton =
            findViewById<Button>(R.id.buttonSave)

        val db =
            DatabaseProvider.getDatabase(this)

        thread {

            val users =
                db.userDao().getAllUsernames()

            runOnUiThread {

                userSpinner.adapter =
                    ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_item,
                        users
                    )
            }
        }

        statusSpinner.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                TicketStatus.values().map { it.name }
            )

        prioritySpinner.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                TicketPriority.values().map { it.name }
            )

        saveButton.setOnClickListener {
            Toast.makeText(
                this,
                "Save clicked",
                Toast.LENGTH_SHORT
            ).show()
            val title =
                titleField.text.toString()

            val description =
                descriptionField.text.toString()

            if (title.isBlank() || description.isBlank()) {

                Toast.makeText(
                    this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val assignedUser =
                userSpinner.selectedItem.toString()

            val status =
                statusSpinner.selectedItem.toString()

            val priority =
                prioritySpinner.selectedItem.toString()

            val creator =
                intent.getStringExtra("username") ?: ""

            thread {

                db.ticketDao().insert(
                    Ticket(
                        title = title,
                        description = description,
                        creator = creator,
                        assignedUser = assignedUser,
                        status = status,
                        priority = priority,
                        createdAt = System.currentTimeMillis()
                    )
                )

                runOnUiThread {

                    Toast.makeText(
                        this,
                        "Ticket created",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }
        }
    }
}