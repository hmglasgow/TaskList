package com.example.tasklist.viewmodel

import androidx.lifecycle.ViewModel
import java.util.Calendar

class AddEditViewModel : ViewModel() {

    var year = 2022
    var month = 4
    var day = 23

    init {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, 1)
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DATE)
    }
}