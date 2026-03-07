package com.example.ramadan.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProgressDao {

    @Query("SELECT * FROM user_progress ORDER BY date DESC")
    fun getAllProgress(): Flow<List<UserProgress>>

    @Query("SELECT * FROM user_progress WHERE date = :date")
    suspend fun getProgressByDate(date: String): UserProgress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: UserProgress)

    @Query("SELECT SUM(prayersOnTime) FROM user_progress")
    fun getTotalPrayers(): Flow<Int?>

    @Query("SELECT SUM(quranPages) FROM user_progress")
    fun getTotalQuranPages(): Flow<Int?>

    @Query("SELECT SUM(sadaqah) FROM user_progress")
    fun getTotalSadaqah(): Flow<Int?>

    @Query("SELECT COUNT(*) FROM user_progress WHERE prayersOnTime > 0")
    fun getStreakDays(): Flow<Int?>
    @Query("SELECT * FROM user_progress ORDER BY date DESC LIMIT 7")
    fun getLast7Days(): Flow<List<UserProgress>>
}
