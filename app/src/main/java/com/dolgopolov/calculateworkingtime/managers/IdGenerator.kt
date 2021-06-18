package com.dolgopolov.calculateworkingtime.managers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class IdGenerator {
    fun getRandomId() = Random.nextInt()

    @SuppressLint("SimpleDateFormat")
    fun getIdByDate(calendar: Calendar = Calendar.getInstance()): Int {
        val ft = SimpleDateFormat("yyyyMMdd")
        return ft.format(calendar.time).toInt()
    }
}