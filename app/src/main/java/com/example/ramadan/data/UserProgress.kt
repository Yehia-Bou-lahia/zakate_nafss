package com.example.ramadan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey
    val date: String,
    val prayersOnTime: Int = 0,
    val quranPages: Int = 0,
    val fastingHours: Int = 0,
    val sadaqah: Int = 0
)
