package com.dolgopolov.calculateworkingtime.repositories

import android.app.Activity
import android.content.Context
import androidx.core.content.edit

object SettingSaver {
    private const val SETTINGS_FILE = "settings"
    const val WORKING_HOURS_IN_DAY = "w_h_in_day"

    fun save(key: String, value: Any, context: Context) =
        context.getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE).edit {
            putString(key, value.toString())
        }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, context: Context) =
        context.getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE).getString(key, String()) as T
}