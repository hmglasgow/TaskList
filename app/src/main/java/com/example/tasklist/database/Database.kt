package com.example.tasklist.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)

const val DATABASE_NAME = "HJGFDCHGF"
const val TABLE_TASKS = "tasks"
const val KEY_ID = "id"
const val KEY_DESCRIPTION = "description"
const val KEY_DAY = "day"
const val KEY_MONTH = "month"
const val KEY_YEAR = "year"
const val KEY_HOUR = "hour"
const val KEY_MINUTE = "minute"

@RequiresApi(Build.VERSION_CODES.P)
class Database(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DAY + " INTEGER"
                + KEY_MONTH + " INTEGER"
                + KEY_YEAR + " INTEGER"
                + KEY_HOUR + " INTEGER"
                + KEY_MINUTE + " INTEGER" + ")")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
}