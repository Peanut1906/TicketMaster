package com.example.ticketmaster.util

import android.content.Context

object SessionManager {

    private const val PREFS =
        "ticketmaster"

    private const val USERNAME =
        "username"

    fun saveUser(
        context: Context,
        username: String
    ) {

        context.getSharedPreferences(
            PREFS,
            Context.MODE_PRIVATE
        )
            .edit()
            .putString(
                USERNAME,
                username
            )
            .apply()
    }

    fun getUser(
        context: Context
    ): String? {

        return context
            .getSharedPreferences(
                PREFS,
                Context.MODE_PRIVATE
            )
            .getString(
                USERNAME,
                null
            )
    }

    fun logout(
        context: Context
    ) {

        context
            .getSharedPreferences(
                PREFS,
                Context.MODE_PRIVATE
            )
            .edit()
            .clear()
            .apply()
    }
}