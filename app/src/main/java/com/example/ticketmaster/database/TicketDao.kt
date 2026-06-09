package com.example.ticketmaster.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ticketmaster.model.Ticket

@Dao
interface TicketDao {

    @Insert
    fun insert(ticket: Ticket)

    @Update
    fun update(ticket: Ticket)

    @Query("SELECT * FROM tickets ORDER BY createdAt DESC")
    fun getAllTickets(): List<Ticket>

    @Query("SELECT * FROM tickets WHERE id = :id")
    fun getTicket(id: Int): Ticket

    @Query("DELETE FROM tickets WHERE id = :id")
    fun delete(id: Int)
}