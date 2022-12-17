package com.example.tasklist.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tasklist.data.Task

@RequiresApi(Build.VERSION_CODES.P)

const val DATABASE_NAME = "kjhgdfghdfghfdgd"
const val TABLE_TASKS = "tasks"
const val KEY_ID = "id"
const val KEY_DESCRIPTION = "description"
const val KEY_DAY = "day"
const val KEY_MONTH = "month"
const val KEY_YEAR = "year"
const val KEY_HOUR = "hour"
const val KEY_MINUTE = "minute"
const val KEY_REPEAT = "repeat"
const val KEY_OTHER_TYPE = "otherType"
const val KEY_OTHER_NUMBER = "otherNumber"
const val KEY_SPECIFIC_NUMBER = "specificNumber"

@RequiresApi(Build.VERSION_CODES.P)
class Database(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            ("CREATE TABLE $TABLE_TASKS($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_DESCRIPTION TEXT, $KEY_DAY INTEGER, $KEY_MONTH INTEGER, $KEY_YEAR INTEGER, $KEY_HOUR INTEGER, $KEY_MINUTE INTEGER, $KEY_REPEAT INTEGER, $KEY_OTHER_TYPE INTEGER, $KEY_OTHER_NUMBER INTEGER, $KEY_SPECIFIC_NUMBER INTEGER)")
        db?.execSQL(createTable)
    }

    fun insert(task: Task) {
        val db = writableDatabase
        val cValues = ContentValues()
        cValues.put(KEY_DESCRIPTION, task.description)
        cValues.put(KEY_DAY, task.day)
        cValues.put(KEY_MONTH, task.month)
        cValues.put(KEY_YEAR, task.year)
        cValues.put(KEY_HOUR, task.hour)
        cValues.put(KEY_MINUTE, task.minute)
        cValues.put(KEY_REPEAT, task.repeat)
        cValues.put(KEY_OTHER_TYPE, task.otherType)
        cValues.put(KEY_OTHER_NUMBER, task.otherNumber)
        cValues.put(KEY_SPECIFIC_NUMBER, task.specificNumber)
        db.insert(TABLE_TASKS, null, cValues)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
    fun readAll(): List<Task> {
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TASKS", emptyArray())
        val results = mutableListOf<Task>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val description = cursor.getString(1)
            val day = cursor.getInt(2)
            val month = cursor.getInt(3)
            val year = cursor.getInt(4)
            val hour = cursor.getInt(5)
            val minute = cursor.getInt(6)
            val repeat = cursor.getInt(7)
            val otherType = cursor.getInt(8)
            val otherNumber = cursor.getInt(9)
            val specificNumber = cursor.getInt(10)
            val task = Task(
                id = id,
                description = description,
                day = day,
                month = month,
                year = year,
                hour = hour,
                minute = minute,
                repeat = repeat,
                otherType = otherType,
                otherNumber = otherNumber,
                specificNumber = specificNumber
            )
            results.add(task)
        }
        return results.sortedBy { it.calculateDate() }
    }

    fun readTask(id: Int): Task? {
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TASKS WHERE $KEY_ID = ?", arrayOf("$id"))
        return if (cursor.moveToNext()) {
            val description = cursor.getString(1)
            val day = cursor.getInt(2)
            val month = cursor.getInt(3)
            val year = cursor.getInt(4)
            val hour = cursor.getInt(5)
            val minute = cursor.getInt(6)
            val repeat = cursor.getInt(7)
            val otherType = cursor.getInt(8)
            val otherNumber = cursor.getInt(9)
            val specificNumber = cursor.getInt(10)
            Task(
                id = id,
                description = description,
                day = day,
                month = month,
                year = year,
                hour = hour,
                minute = minute,
                repeat = repeat,
                otherType = otherType,
                otherNumber = otherNumber,
                specificNumber = specificNumber
            )
        } else {
            null
        }
    }

    fun update(
        id: Int,
        description: String,
        day: Int,
        month: Int,
        year: Int,
        hour: Int,
        minute: Int,
        repeat: Int,
        otherType: Int,
        otherNumber: Int,
        specificNumber: Int
    ) {
        val db = writableDatabase
        val cValues = ContentValues()
        cValues.put(KEY_DESCRIPTION, description)
        cValues.put(KEY_DAY, day)
        cValues.put(KEY_MONTH, month)
        cValues.put(KEY_YEAR, year)
        cValues.put(KEY_HOUR, hour)
        cValues.put(KEY_MINUTE, minute)
        cValues.put(KEY_REPEAT, repeat)
        cValues.put(KEY_OTHER_TYPE, otherType)
        cValues.put(KEY_OTHER_NUMBER, otherNumber)
        cValues.put(KEY_SPECIFIC_NUMBER, specificNumber)
        db.update(TABLE_TASKS, cValues, "$KEY_ID = ?", arrayOf("$id"))
    }

    fun delete(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_TASKS, "$KEY_ID = ?", arrayOf("$id"))
    }

}