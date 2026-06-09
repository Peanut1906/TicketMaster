package com.example.ticketmaster.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ticketmaster.model.Comment

@Dao
interface CommentDao {

    @Insert
    fun insert(comment: Comment)

    @Query("SELECT * FROM comments WHERE ticketId = :ticketId")
    fun getComments(ticketId: Int): List<Comment>
}