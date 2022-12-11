package com.example.tasklist.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi


@RequiresApi(Build.VERSION_CODES.P)

const val DATABASE_NAME = "HJGFDCHGFkjhkj"
const val TABLE_TASKS = "tasks"
const val KEY_ID = "id"
const val KEY_DESCRIPTION = "description"
const val KEY_DAY = "day"
const val KEY_MONTH = "month"
const val KEY_YEAR = "year"
const val KEY_HOUR = "hour"
const val KEY_MINUTE = "minute"
const val KEY_REPEAT = "repeat"

@RequiresApi(Build.VERSION_CODES.P)
class Database(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DAY + " INTEGER,"
                + KEY_MONTH + " INTEGER,"
                + KEY_YEAR + " INTEGER,"
                + KEY_HOUR + " INTEGER,"
                + KEY_MINUTE + " INTEGER,"
                + KEY_REPEAT + " INTEGER"
                + ")")
        db?.execSQL(createTable)
    }

    fun insert(
        description: String,
        day: Int,
        month: Int,
        year: Int,
        hour: Int,
        minute: Int,
        repeat: Int
    ) {
        val db = this.writableDatabase
        val cValues = ContentValues()
        cValues.put(KEY_DESCRIPTION, description)
        cValues.put(KEY_DAY, day)
        cValues.put(KEY_MONTH, month)
        cValues.put(KEY_YEAR, year)
        cValues.put(KEY_HOUR, hour)
        cValues.put(KEY_MINUTE, minute)
        cValues.put(KEY_REPEAT, repeat)
        db.insert(TABLE_TASKS, null, cValues)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
}