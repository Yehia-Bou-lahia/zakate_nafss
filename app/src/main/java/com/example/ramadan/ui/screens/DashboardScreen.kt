package com.example.ramadan.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ramadan.R
import com.example.ramadan.data.DashboardViewModel
import com.example.ramadan.ui.theme.AlmaraiFont
import com.example.ramadan.ui.theme.BgBottom
import com.example.ramadan.ui.theme.BgMid
import com.example.ramadan.ui.theme.BgTop
import com.example.ramadan.ui.theme.GoldColor
import com.example.ramadan.ui.theme.IbmPlexArabicFont
import com.example.ramadan.ui.theme.SubtitleColor
import com.example.ramadan.ui.theme.WhiteColor
import java.util.Calendar

// ── تحويل التاريخ باستخدام UmmalquraCalendar ──────────
fun getHijriDate(): Triple<Int, String, Int> {
    val hijriCal = com.github.msarhan.ummalqura.calendar.UmmalquraCalendar()
    val hijriMonths = listOf(
        "محرم", "صفر", "ربيع الأول", "ربيع الآخر",
        "جمادى الأولى", "جمادى الآخرة", "رجب", "شعبان",
        "رمضان", "شوال", "ذو القعدة", "ذو الحجة"
    )
    val day   = hijriCal.get(com.github.msarhan.ummalqura.calendar.UmmalquraCalendar.DAY_OF_MONTH)
    val month = hijriCal.get(com.github.msarhan.ummalqura.calendar.UmmalquraCalendar.MONTH)
    val year  = hijriCal.get(com.github.msarhan.ummalqura.calendar.UmmalquraCalendar.YEAR)
    return Triple(day, hijriMonths[month], year)
}

// ── اليوم الحالي بالعربية ──────────────────────────────
fun getTodayArabic(): String {
    val days = listOf("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت")
    return days[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1]
}

@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel = viewModel()
) {
    val (hijriDay, hijriMonth, hijriYear) = remember { getHijriDate() }
    val todayArabic = remember { getTodayArabic() }

    val totalPrayers by dashboardViewModel.totalPrayers.collectAsState()
    val totalQuran   by dashboardViewModel.totalQuranPages.collectAsState()
    val totalSadaqah by dashboardViewModel.totalSadaqah.collectAsState()
    val streakDays   by dashboardViewModel.streakDays.collectAsState()
    val last7Days    by dashboardViewModel.last7Days.collectAsState()
    val latestAchievements by dashboardViewModel.latestAchievements.collectAsState()

    val chartData = last7Days
        .map { it.prayersOnTime.toFloat() + it.quranPages.toFloat() }
        .reversed()
        .let { list ->
            if (list.size < 7) List(7 - list.size) { 0f } + list
            else list
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to BgTop,
                        0.5f to BgMid,
                        1.0f to BgBottom
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(52.dp))

            // ══ TopBar ══════════════════════════════════════
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(WhiteColor.copy(alpha = 0.08f))
                        .border(1.dp, WhiteColor.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .width(18.dp)
                                    .height(2.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(WhiteColor)
                            )
                        }
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(WhiteColor.copy(alpha = 0.08f))
                        .border(2.dp, GoldColor, CircleShape)
                ) {
                    Text(
                        text = "م",
                        color = GoldColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = AlmaraiFont
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ══ العنوان الرئيسي ════════════════════════════
            Text(
                text = "رحلتي الرمضانية",
                fontFamily = AlmaraiFont,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                color = WhiteColor,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

            // ══ العنوان الثانوي ════════════════════════════
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "استمر في التقدم", color = SubtitleColor, fontFamily = IbmPlexArabicFont, fontSize = 13.sp)
                Text(text = " • ", color = SubtitleColor, fontSize = 13.sp)
                Text(
                    text = "اليوم $hijriDay من 30",
                    color = GoldColor,
                    fontFamily = IbmPlexArabicFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ══ بطاقة التاريخ الهجري ══════════════════════
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(WhiteColor.copy(alpha = 0.06f))
                    .border(1.dp, WhiteColor.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Image(
                            painter = painterResource(id = R.drawable.hilaldb),
                            contentDescription = null,
                            modifier = Modifier.size(78.dp).padding(4.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "$hijriYear هـ", color = SubtitleColor, fontFamily = IbmPlexArabicFont, fontSize = 12.sp)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = todayArabic, color = SubtitleColor, fontFamily = IbmPlexArabicFont, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$hijriDay $hijriMonth",
                            fontFamily = AlmaraiFont,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 26.sp,
                            color = WhiteColor
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(2.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Brush.horizontalGradient(listOf(Color(0x00C9A84C), GoldColor)))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ══ شبكة الإحصائيات — الصف الأول ══════════════
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = "${totalPrayers ?: 0}",
                    label = "صلوات في وقتها",
                    badge = "اليوم",
                    badgeColor = Color(0xFF4A90D9),
                    iconRes = R.drawable.mosque,
                    progressColor = Color(0xFF4A90D9),
                    progress = ((totalPrayers ?: 0) / 150f).coerceIn(0f, 1f)
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = "${totalQuran ?: 0}",
                    label = "أجزاء مكتملة",
                    badge = "الهدف: 30",
                    badgeColor = Color(0xFF2ECC71),
                    iconRes = R.drawable.asar,
                    progressColor = Color(0xFF2ECC71),
                    progress = ((totalQuran ?: 0) / 30f).coerceIn(0f, 1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ══ شبكة الإحصائيات — الصف الثاني ═════════════
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = "${streakDays ?: 0} يوم",
                    label = "التتابع الحالي",
                    badge = "المتوسط",
                    badgeColor = Color(0xFF9B59B6),
                    iconRes = R.drawable.hillal,
                    progressColor = Color(0xFF9B59B6),
                    progress = ((streakDays ?: 0) / 30f).coerceIn(0f, 1f)
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = "${totalSadaqah ?: 0}",
                    label = "صدقات ممنوحة",
                    badge = "ريال",
                    badgeColor = Color(0xFFE67E22),
                    iconRes = R.drawable.star,
                    progressColor = Color(0xFFE67E22),
                    progress = ((totalSadaqah ?: 0) / 500f).coerceIn(0f, 1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ══ الرسم البياني النمو الروحاني ═══════════════
            SpiritualGrowthCard(data = chartData)

            Spacer(modifier = Modifier.height(12.dp))

            // ══ بطاقة الإنجازات ════════════════════════════
            AchievementsCard(achievements = latestAchievements)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


// ── بطاقة إحصاء ──────────────────────────────────────
@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    badge: String,
    badgeColor: Color,
    iconRes: Int,
    progressColor: Color,
    progress: Float
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(WhiteColor.copy(alpha = 0.06f))
            .border(1.dp, WhiteColor.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(badgeColor.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = badge, color = badgeColor, fontFamily = IbmPlexArabicFont, fontSize = 11.sp)
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(badgeColor.copy(alpha = 0.15f))
                ) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontFamily = AlmaraiFont,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                color = WhiteColor,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = label,
                color = SubtitleColor,
                fontFamily = IbmPlexArabicFont,
                fontSize = 12.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(WhiteColor.copy(alpha = 0.08f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress)
                        .clip(RoundedCornerShape(50))
                        .background(progressColor)
                )
            }
        }
    }
}


// ── بطاقة النمو الروحاني ──────────────────────────────
@Composable
fun SpiritualGrowthCard(data: List<Float>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(WhiteColor.copy(alpha = 0.06f))
            .border(1.dp, WhiteColor.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(GoldColor.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(text = "آخر 7 أيام", color = GoldColor, fontFamily = IbmPlexArabicFont, fontSize = 11.sp)
                }
                Text(
                    text = "النمو الروحاني 📈",
                    color = WhiteColor,
                    fontFamily = AlmaraiFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                val points = if (data.isEmpty()) List(7) { 0f } else data
                val maxVal = points.maxOrNull()?.takeIf { it > 0f } ?: 1f
                val stepX = size.width / (points.size - 1).coerceAtLeast(1)
                val goldColor = Color(0xFFC9A84C)

                val linePath = Path()
                val fillPath = Path()

                points.forEachIndexed { i, value ->
                    val x = i * stepX
                    val y = size.height - (value / maxVal) * size.height * 0.85f
                    if (i == 0) {
                        linePath.moveTo(x, y)
                        fillPath.moveTo(x, size.height)
                        fillPath.lineTo(x, y)
                    } else {
                        val prevX = (i - 1) * stepX
                        val prevY = size.height - (points[i - 1] / maxVal) * size.height * 0.85f
                        val cpX = (prevX + x) / 2f
                        linePath.cubicTo(cpX, prevY, cpX, y, x, y)
                        fillPath.cubicTo(cpX, prevY, cpX, y, x, y)
                    }
                }

                fillPath.lineTo((points.size - 1) * stepX, size.height)
                fillPath.close()

                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(goldColor.copy(alpha = 0.35f), Color(0x00C9A84C)),
                        startY = 0f,
                        endY = size.height
                    )
                )

                drawPath(
                    path = linePath,
                    color = goldColor,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )

                points.forEachIndexed { i, value ->
                    val x = i * stepX
                    val y = size.height - (value / maxVal) * size.height * 0.85f
                    drawCircle(color = goldColor, radius = 5.dp.toPx(), center = Offset(x, y))
                    drawCircle(color = Color(0xFF0A1A0A), radius = 3.dp.toPx(), center = Offset(x, y))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val dayLabels = listOf("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت")
            val todayIndex = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                (6 downTo 0).forEach { offset ->
                    val dayIdx = ((todayIndex - offset) + 7) % 7
                    Text(
                        text = dayLabels[dayIdx].take(2),
                        color = if (offset == 0) GoldColor else SubtitleColor,
                        fontFamily = IbmPlexArabicFont,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}


// ── بطاقة الإنجازات ───────────────────────────────────
@Composable
fun AchievementsCard(achievements: List<com.example.ramadan.data.Achievement>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(WhiteColor.copy(alpha = 0.06f))
            .border(1.dp, WhiteColor.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF9B59B6).copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(text = "الأخيرة", color = Color(0xFF9B59B6), fontFamily = IbmPlexArabicFont, fontSize = 11.sp)
                }
                Text(
                    text = "🏆 الإنجازات",
                    color = WhiteColor,
                    fontFamily = AlmaraiFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (achievements.isEmpty()) {
                // ── حالة فارغة ────────────────────────
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = "لا توجد إنجازات بعد — ابدأ رحلتك! 🌙",
                        color = SubtitleColor,
                        fontFamily = IbmPlexArabicFont,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                achievements.forEachIndexed { index, achievement ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(WhiteColor.copy(alpha = 0.04f))
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = achievement.date,
                            color = SubtitleColor,
                            fontFamily = IbmPlexArabicFont,
                            fontSize = 11.sp
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = achievement.title,
                                color = WhiteColor,
                                fontFamily = IbmPlexArabicFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(GoldColor.copy(alpha = 0.15f))
                            ) {
                                Text(text = achievement.emoji, fontSize = 18.sp)
                            }
                        }
                    }

                    if (index < achievements.size - 1) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}