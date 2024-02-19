package com.example.todoapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var todoItem: String? = null,
    var dateTime: Date? = Calendar.getInstance().time
)