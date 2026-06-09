package com.example.ticketmaster.util

import com.example.ticketmaster.model.Ticket
import com.example.ticketmaster.model.TicketPriority
import com.example.ticketmaster.model.TicketStatus
import com.example.ticketmaster.model.User

object DataSeeder {

    fun seedUsers(): List<User> {

        return listOf(
            User(username = "admin", password = "admin"),
            User(username = "alice", password = "alice"),
            User(username = "bob", password = "bob")
        )
    }

    fun seedTickets(): List<Ticket> {

        return listOf(
            Ticket(
                title = "Printer not working",
                description = "Main office printer is offline",
                creator = "admin",
                assignedUser = "alice",
                status = TicketStatus.OPEN.name,
                priority = TicketPriority.HIGH.name,
                createdAt = System.currentTimeMillis()
            ),

            Ticket(
                title = "WiFi issue",
                description = "Intermittent connectivity on 3rd floor",
                creator = "alice",
                assignedUser = "bob",
                status = TicketStatus.IN_PROGRESS.name,
                priority = TicketPriority.MEDIUM.name,
                createdAt = System.currentTimeMillis()
            ),

            Ticket(
                title = "Software installation",
                description = "Need IntelliJ installed on new machine",
                creator = "bob",
                assignedUser = "admin",
                status = TicketStatus.WAITING.name,
                priority = TicketPriority.LOW.name,
                createdAt = System.currentTimeMillis()
            )
        )
    }
}