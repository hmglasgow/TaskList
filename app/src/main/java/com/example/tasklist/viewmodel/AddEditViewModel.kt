package com.example.tasklist.viewmodel

import androidx.lifecycle.ViewModel
import java.util.Calendar

class AddEditViewModel : ViewModel() {

    var year: Int
    var month: Int
    var day: Int
    var hour: Int
    var minute: Int

    init {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 1)
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DATE)
        hour = 6
        minute = 0
    }
}