package com.example.tasklist.viewmodel

import androidx.lifecycle.ViewModel
import java.util.*

class AddEditViewModel : ViewModel() {

    var id: Int = 0
    var description: String = ""
    var year: Int
    var month: Int
    var day: Int
    var hour: Int
    var minute: Int
    var repeat: Int = 0

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