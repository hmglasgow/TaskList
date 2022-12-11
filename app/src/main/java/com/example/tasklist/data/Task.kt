package com.example.tasklist.data

data class Task(var id: Int,
                var description: String,
                var year: Int,
                var month: Int,
                var day: Int,
                var hour: Int,
                var minute: Int,
                var repeat: Int)