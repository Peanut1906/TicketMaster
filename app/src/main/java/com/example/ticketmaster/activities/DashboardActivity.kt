package com.example.ticketmaster.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketmaster.R
import com.example.ticketmaster.adapter.TicketAdapter
import com.example.ticketmaster.model.Ticket
import com.example.ticketmaster.util.DataSeeder
import com.example.ticketmaster.util.DatabaseProvider
import com.example.ticketmaster.util.FirstRunManager
import com.example.ticketmaster.util.SessionManager
import kotlin.concurrent.thread

class DashboardActivity : AppCompatActivity() {

    private var allTickets: List<Ticket> = listOf()

    private var selectedStatus = "ALL"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)

        val createTicketButton =
            findViewById<Button>(R.id.buttonCreateTicket)

        createTicketButton.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    CreateTicketActivity::class.java
                )
            )
        }

        val searchBox =
            findViewById<EditText>(R.id.searchBox)

        val statusFilter =
            findViewById<Spinner>(R.id.statusFilter)

        val recyclerView =
            findViewById<RecyclerView>(R.id.ticketRecycler)

        val logoutButton =
            findViewById<Button>(R.id.buttonLogout)

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        val statuses =
            listOf(
                "ALL",
                "OPEN",
                "IN_PROGRESS",
                "WAITING",
                "CLOSED"
            )

        statusFilter.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                statuses
            )

        logoutButton.setOnClickListener {

            SessionManager.logout(this)

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }

        searchBox.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    updateList(s.toString())
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                }
            }
        )

        statusFilter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    selectedStatus =
                        statuses[position]

                    updateList(
                        searchBox.text.toString()
                    )
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {
                }
            }

        initializeDatabase()
    }

    override fun onResume() {

        super.onResume()

        loadTickets()
    }

    private fun initializeDatabase() {

        thread {

            val db =
                DatabaseProvider.getDatabase(this)

            if (FirstRunManager.isFirstRun(this)) {

                DataSeeder.seedUsers().forEach {
                    db.userDao().insert(it)
                }

                DataSeeder.seedTickets().forEach {
                    db.ticketDao().insert(it)
                }

                FirstRunManager.setNotFirstRun(this)
            }

            loadTickets()
        }
    }

    private fun loadTickets() {

        thread {

            val db =
                DatabaseProvider.getDatabase(this)

            allTickets =
                db.ticketDao().getAllTickets()

            runOnUiThread {

                updateList(
                    findViewById<EditText>(
                        R.id.searchBox
                    ).text.toString()
                )
            }
        }
    }

    private fun updateList(
        query: String
    ) {

        val filtered =
            allTickets.filter {

                val matchesTitle =
                    it.title.contains(
                        query,
                        ignoreCase = true
                    )

                val matchesStatus =
                    selectedStatus == "ALL" ||
                            it.status == selectedStatus

                matchesTitle &&
                        matchesStatus
            }

        findViewById<RecyclerView>(
            R.id.ticketRecycler
        ).adapter =
            TicketAdapter(filtered)
    }
}