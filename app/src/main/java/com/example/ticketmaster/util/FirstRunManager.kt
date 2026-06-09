package com.example.ticketmaster.util

import android.content.Context

object FirstRunManager {

    private const val PREFS = "ticketmaster_prefs"
    private const val KEY_FIRST_RUN = "first_run"

    fun isFirstRun(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_FIRST_RUN, true)
    }

    fun setNotFirstRun(context: Context) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_FIRST_RUN, false).apply()
    }
}