package com.example.tasklist.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tasklist.data.Task
import java.util.*

class AddEditViewModel : ViewModel() {

    var id: Int = 0
    var description: String = ""
    var year: Int
    var month: Int
    var day: Int
    var hour: Int = 6
    var minute: Int = 0
    var repeat: Int = Task.repeatOther
    var otherType: Int = Task.otherWeeks
    var otherNumber: Int = 2

    init {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 1)
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DATE)
    }
}