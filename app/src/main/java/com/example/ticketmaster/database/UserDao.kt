package com.example.ticketmaster.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ticketmaster.model.User

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    fun getUser(username: String): User?

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    fun login(username: String, password: String): User?

    @Query("SELECT username FROM users")
    fun getAllUsernames(): List<String>
}