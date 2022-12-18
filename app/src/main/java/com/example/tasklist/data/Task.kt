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
    var repeat: Int,
    var otherType: Int,
    var otherNumber: Int,
    var specificNumber: Int
) {

    companion object {
        const val repeatNone = 0
        const val repeatDaily = 1
        const val repeatWeekDays = 2
        const val repeatWeekly = 3
        const val repeatMonthly = 4
        const val repeatYearly = 5
        const val repeatOther = 6
        const val repeatSpecific = 7

        const val otherDays = 0
        const val otherWeeks = 1
        const val otherMonths = 2
        const val otherYears = 3
    }

    fun uptick(): Task {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DATE, day)
        when (repeat) {
            repeatDaily -> {
                cal.add(Calendar.DATE, 1)
            }
            repeatWeekDays -> {
                do {
                    cal.add(Calendar.DATE, 1)
                } while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            }
            repeatWeekly -> {
                cal.add(Calendar.DATE, 7)
            }
            repeatMonthly -> {
                cal.add(Calendar.MONTH, 1)
            }
            repeatYearly -> {
                cal.add(Calendar.YEAR, 1)
            }
            repeatOther -> {
                var otherNumberLocal = otherNumber
                val otherTypeLocal = when (otherType) {
                    otherDays -> Calendar.DATE
                    otherWeeks -> {
                        otherNumberLocal *= 7
                        Calendar.DATE
                    }
                    otherMonths -> Calendar.MONTH
                    else -> Calendar.YEAR
                }
                cal.add(otherTypeLocal, otherNumberLocal)
            }
            repeatSpecific -> {
                if (specificNumber > 0) {
                    var gotIt = false
                    cal.add(Calendar.DATE, 1)
                    do {
                        when (cal.get(Calendar.DAY_OF_WEEK)) {
                            Calendar.MONDAY -> {
                                if (specificNumber.mod(1) == 1) gotIt = true
                            }
                            Calendar.TUESDAY -> {
                                if (specificNumber.mod(2) == 2) gotIt = true
                            }
                            Calendar.WEDNESDAY -> {
                                if (specificNumber.mod(4) == 4) gotIt = true
                            }
                            Calendar.THURSDAY -> {
                                if (specificNumber.mod(8) == 8) gotIt = true
                            }
                            Calendar.FRIDAY -> {
                                if (specificNumber.mod(16) == 16) gotIt = true
                            }
                            Calendar.SATURDAY -> {
                                if (specificNumber.mod(32) == 32) gotIt = true
                            }
                            Calendar.SUNDAY -> {
                                if (specificNumber.mod(64) == 64) gotIt = true
                            }
                        }
                        if (!gotIt) {
                            cal.add(Calendar.DATE, 1)
                        }
                    } while (!gotIt)
                }
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
            repeat = repeat,
            otherType = otherType,
            otherNumber = otherNumber,
            specificNumber = specificNumber
        )
    }

    fun calculateDate() : Date {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DATE, day)
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.DATE, day)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.time
    }

    fun overdue(): Boolean {
        val cal = Calendar.getInstance()
        val now = cal.time

        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DATE, day)
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.DATE, day)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        val then = cal.time
        return now.after(then)
    }

}