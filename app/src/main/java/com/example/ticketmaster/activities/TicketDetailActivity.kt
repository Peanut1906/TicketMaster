package com.example.ticketmaster.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaster.R
import com.example.ticketmaster.adapter.CommentAdapter
import com.example.ticketmaster.model.Comment
import com.example.ticketmaster.model.Ticket
import com.example.ticketmaster.model.TicketStatus
import com.example.ticketmaster.util.DatabaseProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.concurrent.thread

class TicketDetailActivity : AppCompatActivity() {

    private lateinit var ticket: Ticket

    override fun onCreate(savedInstanceState: Bundle?) {
        Toast.makeText(this, "CreateTicketActivity opened", Toast.LENGTH_SHORT).show()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ticket_detail)

        val ticketId =
            intent.getIntExtra(
                "ticketId",
                -1
            )

        val spinner =
            findViewById<Spinner>(
                R.id.spinnerStatus
            )

        spinner.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                TicketStatus.values().map { it.name }
            )

        findViewById<RecyclerView>(
            R.id.commentRecycler
        ).layoutManager =
            LinearLayoutManager(this)

        loadTicket(ticketId)

        findViewById<Button>(
            R.id.buttonComment
        ).setOnClickListener {

            val text =
                findViewById<EditText>(
                    R.id.editComment
                ).text.toString()

            if (text.isBlank()) {
                return@setOnClickListener
            }

            thread {

                DatabaseProvider
                    .getDatabase(this)
                    .commentDao()
                    .insert(
                        Comment(
                            ticketId = ticket.id,
                            author = "User",
                            message = text,
                            timestamp = System.currentTimeMillis()
                        )
                    )

                runOnUiThread {

                    findViewById<EditText>(
                        R.id.editComment
                    ).setText("")

                    loadComments()
                }
            }
        }

        findViewById<Button>(
            R.id.buttonSaveStatus
        ).setOnClickListener {

            val updatedTicket =
                ticket.copy(
                    title = findViewById<EditText>(
                        R.id.editTitle
                    ).text.toString(),

                    description = findViewById<EditText>(
                        R.id.editDescription
                    ).text.toString(),

                    status = spinner.selectedItem.toString()
                )

            thread {

                DatabaseProvider
                    .getDatabase(this)
                    .ticketDao()
                    .update(updatedTicket)

                runOnUiThread {

                    Toast.makeText(
                        this,
                        "Ticket updated",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }
        }

        findViewById<Button>(
            R.id.buttonDelete
        ).setOnClickListener {

            thread {

                DatabaseProvider
                    .getDatabase(this)
                    .ticketDao()
                    .delete(ticket.id)

                runOnUiThread {

                    Toast.makeText(
                        this,
                        "Ticket deleted",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }
        }
    }

    private fun loadTicket(ticketId: Int) {

        thread {

            val loadedTicket =
                DatabaseProvider
                    .getDatabase(this)
                    .ticketDao()
                    .getTicket(ticketId)

            runOnUiThread {

                ticket = loadedTicket

                populateTicket()

                loadComments()
            }
        }
    }

    private fun populateTicket() {

        val titleEdit =
            findViewById<EditText>(
                R.id.editTitle
            )

        val descEdit =
            findViewById<EditText>(
                R.id.editDescription
            )

        titleEdit.setText(ticket.title)

        descEdit.setText(ticket.description)

        findViewById<TextView>(
            R.id.textCreator
        ).text =
            "Creator: ${ticket.creator}"

        findViewById<TextView>(
            R.id.textAssigned
        ).text =
            "Assigned: ${ticket.assignedUser}"

        val formatter =
            SimpleDateFormat(
                "dd.MM.yyyy HH:mm",
                Locale.getDefault()
            )

        findViewById<TextView>(
            R.id.textDate
        ).text =
            formatter.format(
                Date(ticket.createdAt)
            )

        val spinner =
            findViewById<Spinner>(
                R.id.spinnerStatus
            )

        val position =
            TicketStatus.values()
                .indexOfFirst {
                    it.name == ticket.status
                }

        spinner.setSelection(position)
    }

    private fun loadComments() {

        thread {

            val comments =
                DatabaseProvider
                    .getDatabase(this)
                    .commentDao()
                    .getComments(ticket.id)

            runOnUiThread {

                findViewById<RecyclerView>(
                    R.id.commentRecycler
                ).adapter =
                    CommentAdapter(comments)
            }
        }
    }
}