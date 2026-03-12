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
@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val emoji: String,
    val title: String,
    val date: String
)
@Entity(tableName = "quran_progress")
data class QuranProgress(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val juzNumber: Int,
    val khatmaNumber: Int,
    val isCompleted: Boolean = false,
    val completedDate: String? = null
)
