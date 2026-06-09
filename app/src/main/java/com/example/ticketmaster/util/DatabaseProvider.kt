package com.example.ticketmaster.util

import android.content.Context
import com.example.ticketmaster.database.AppDatabase

object DatabaseProvider {

    private var db: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {

        if (db == null) {

            db = AppDatabase.getDatabase(context)
        }

        return db!!
    }
}