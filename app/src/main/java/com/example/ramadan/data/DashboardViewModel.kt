package com.example.ramadan.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).userProgressDao()

    // ── إجماليات للـ Dashboard ────────────────────
    val totalPrayers = dao.getTotalPrayers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    val totalQuranPages = dao.getTotalQuranPages()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    val totalSadaqah = dao.getTotalSadaqah()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    val streakDays = dao.getStreakDays()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)
    val last7Days = dao.getLast7Days()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    // ── إضافة تقدم اليوم ─────────────────────────
    suspend fun addTodayProgress(
        prayers: Int = 0,
        quranPages: Int = 0,
        fastingHours: Int = 0,
        sadaqah: Int = 0
    ) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
        val existing = dao.getProgressByDate(today)
        dao.insert(
            UserProgress(
                date = today,
                prayersOnTime = (existing?.prayersOnTime ?: 0) + prayers,
                quranPages = (existing?.quranPages ?: 0) + quranPages,
                fastingHours = (existing?.fastingHours ?: 0) + fastingHours,
                sadaqah = (existing?.sadaqah ?: 0) + sadaqah
            )
        )
    }
    val latestAchievements = dao.getLatestAchievements()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    suspend fun unlockAchievement(emoji: String, title: String) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
        dao.insertAchievement(Achievement(emoji = emoji, title = title, date = today))
    }
    // ── بيانات القرآن ─────────────────────────────
    val allQuranProgress = dao.getAllQuranProgress()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val completedJuzCount = dao.getCompletedJuzCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    // ── تهيئة الأجزاء عند اختيار عدد الختمات ──────
    suspend fun initQuranProgress(khatmaCount: Int) {
        val existing = dao.getQuranProgressCount()
        if (existing > 0) return  // تم التهيئة مسبقاً

        for (khatma in 1..khatmaCount) {
            for (juz in 1..30) {
                dao.insertQuranProgress(
                    QuranProgress(
                        juzNumber = juz,
                        khatmaNumber = khatma,
                        isCompleted = false
                    )
                )
            }
        }
    }

    // ── تحديد الجزء الحالي ────────────────────────
    fun getCurrentJuz(khatmaCount: Int, completedCount: Int): Int {
        return (completedCount % 30) + 1
    }

    // ── تحديد الختمة الحالية ──────────────────────
    fun getCurrentKhatma(completedCount: Int): Int {
        return (completedCount / 30) + 1
    }

    // ── تعليم جزء كمكتمل ─────────────────────────
    suspend fun completeJuz(juz: Int, khatma: Int) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date())
        dao.markJuzCompleted(juz, khatma, today)

        // فتح إنجاز عند إكمال ختمة كاملة
        if (juz == 30) {
            unlockAchievement("📖", "أكملت الختمة $khatma")
        }
    }

    // ── نسبة التقدم للفانوس (0f - 1f) ────────────
    fun getLanternProgress(khatmaCount: Int, completedCount: Int): Float {
        val total = khatmaCount * 30
        return if (total == 0) 0f else completedCount.toFloat() / total.toFloat()
    }
}