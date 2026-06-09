package com.example.ticketmaster.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets")
data class Ticket(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val creator: String,
    val assignedUser: String,
    val status: String,
    val priority: String,
    val createdAt: Long
)