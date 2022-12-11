package com.example.tasklist

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
            2 -> "February"
            3 -> "February"
            4 -> "February"
            5 -> "February"
            6 -> "February"
            7 -> "February"
            8 -> "February"
            9 -> "February"
            10 -> "February"
            else  -> "December"
        }
        return "$day $mon  $year"
    }
}