package com.example.tasklist

import java.util.*

object Utils {

    fun formatTime(hour: Int, minute: Int) : String {
        val formattedHour = if (hour < 10 ) "0$hour" else hour
        val formattedMinute = if (minute < 10 ) "0$minute" else minute
        return "$formattedHour:$formattedMinute"
    }

    fun formatDate(year: Int, month: Int, day: Int) : String {
        val mon = when (month)
        {
            0 -> "January"
            1 -> "February"
            2 -> "March"
            3 -> "April"
            4 -> "May"
            5 -> "June"
            6 -> "July"
            7 -> "August"
            8 -> "September"
            9 -> "October"
            10 -> "November"
            else  -> "December"
        }

        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DATE, day)
        val dy = when(cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            else -> "Sunday"
        }

        return "$dy, $day $mon $year"
    }

    fun formatRepeat(repeat: Int): String {
        return when (repeat) {
            0 -> "No repeat"
            1 -> "Once a day"
            2 -> "Once a day (Mon to Fri)"
            3 -> "Once a week"
            4 -> "Once a month"
            else -> "Once a year"
        }
    }
}