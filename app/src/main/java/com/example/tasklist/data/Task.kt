package com.example.tasklist.data

import java.util.*

data class Task(
    var id: Int,
    var description: String,
    var year: Int,
    var month: Int,
    var day: Int,
    var hour: Int,
    var minute: Int,
    var repeat: Int
) {

    fun uptick(): Task {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DATE, day)
        when (repeat) {
            1 -> {
                cal.add(Calendar.DATE, 1)
            }
            2 -> {
                do {
                    cal.add(Calendar.DATE, 1)
                } while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            }
            3 -> {
                cal.add(Calendar.DATE, 7)
            }
            4 -> {
                cal.add(Calendar.MONTH, 1)
            }
            5 -> {
                cal.add(Calendar.YEAR, 1)
            }
        }
        return Task(
            id = id,
            description = description,
            year = cal.get(Calendar.YEAR),
            month = cal.get(Calendar.MONTH),
            day = cal.get(Calendar.DATE),
            hour = hour,
            minute = minute,
            repeat = repeat
        )
    }

}