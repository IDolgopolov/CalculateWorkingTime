package com.dolgopolov.calculateworkingtime.repositories

import android.content.Context
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepository @Inject constructor() {
    companion object {
        private const val SETTINGS_FILE = "settings"
        private const val DEFAULT_WORKING_HOURS_IN_DAY = 8
        const val WORKING_HOURS_IN_DAY = "w_h_in_day"
    }

    fun save(key: String, value: Any, context: Context) =
        getSharedPref(context).edit {
            putString(key, value.toString())
        }

    fun get(key: String, context: Context) =
        getSharedPref(context).getString(key, null) ?: getDefValue(key)

    private fun getSharedPref(context: Context) =
        context.getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE)


    private fun getDefValue(key: String): String {
        return when (key) {
            WORKING_HOURS_IN_DAY -> DEFAULT_WORKING_HOURS_IN_DAY.toString()
            else -> String()
        }
    }
}