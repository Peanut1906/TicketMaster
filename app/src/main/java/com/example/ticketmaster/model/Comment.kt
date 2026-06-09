package com.example.ticketmaster.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ticketId: Int,
    val author: String,
    val message: String,
    val timestamp: Long
)