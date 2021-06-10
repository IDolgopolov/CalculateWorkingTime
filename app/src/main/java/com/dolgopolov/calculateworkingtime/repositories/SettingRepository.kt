package com.dolgopolov.calculateworkingtime.repositories

import android.app.Activity
import android.content.Context
import androidx.core.content.edit
import java.lang.IllegalStateException
import javax.inject.Inject

class SettingRepository @Inject constructor() {
    companion object {
        private const val SETTINGS_FILE = "settings"
        private const val DEFAULT_WORKING_HOURS_IN_DAY = 8
        const val WORKING_HOURS_IN_DAY = "w_h_in_day"
    }

    fun save(key: String, value: Any, context: Context) =
        context.getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE).edit {
            putString(key, value.toString())
        }

    @Suppress("UNCHECKED_CAST")
    fun get(key: String, context: Context) =
        context
            .getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE)
            .getString(key, null)
            ?: getDefValue(key)


    private fun getDefValue(key: String): String {
        return when (key) {
            WORKING_HOURS_IN_DAY -> DEFAULT_WORKING_HOURS_IN_DAY.toString()
            else -> String()
        }
    }
}