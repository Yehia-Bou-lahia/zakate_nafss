package com.example.ramadan.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ramadan.R
import com.example.ramadan.data.DashboardViewModel
import com.example.ramadan.data.QuranData
import com.example.ramadan.ui.components.BottomNavBar
import com.example.ramadan.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun QuranScreen(
    dashboardViewModel: DashboardViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()

    // ── اختيار عدد الختمات (محفوظ مؤقتاً في State) ──
    var selectedKhatma by remember { mutableStateOf(1) }

    // ── بيانات من Room ────────────────────────────────
    val allProgress    by dashboardViewModel.allQuranProgress.collectAsState()
    val completedCount by dashboardViewModel.completedJuzCount.collectAsState()

    // ── حسابات التقدم ─────────────────────────────────
    val totalJuz   = selectedKhatma * 30
    val progress   = dashboardViewModel.getLanternProgress(selectedKhatma, completedCount ?: 0)
    val currentJuz = dashboardViewModel.getCurrentJuz(selectedKhatma, completedCount ?: 0)
    val currentKhatma = dashboardViewModel.getCurrentKhatma(completedCount ?: 0)

    // ── قائمة الأجزاء المكتملة من Room ───────────────
    val completedSet = allProgress
        .filter { it.isCompleted }
        .map { it.juzNumber + ((it.khatmaNumber - 1) * 30) }
        .toSet()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedRoute = "quran",
                onItemSelected = { }
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->

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
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(52.dp))

                // ══ TopBar ══════════════════════════════
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

                    Text(
                        text = "ختمة القرآن",
                        fontFamily = AlmaraiFont,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp,
                        color = GoldColor
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(WhiteColor.copy(alpha = 0.08f))
                            .border(1.dp, WhiteColor.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
                    ) {
                        Text(text = "🔔", fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ══ اختيار عدد الختمات ══════════════════
                KhatmaSelector(
                    selected = selectedKhatma,
                    onSelect = { newKhatma ->
                        selectedKhatma = newKhatma
                        scope.launch {
                            dashboardViewModel.initQuranProgress(newKhatma)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // ══ الفانوس ══════════════════════════════
                LanternProgress(progress = progress)

                Spacer(modifier = Modifier.height(20.dp))

                // ══ الجزء الحالي ══════════════════════════
                Text(
                    text = "الجزء $currentJuz من 30",
                    fontFamily = AlmaraiFont,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = WhiteColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "متقدم • ${30 - currentJuz} أيام متبقية",
                    fontFamily = IbmPlexArabicFont,
                    fontSize = 13.sp,
                    color = SubtitleColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ══ بطاقة الهدف اليومي ═══════════════════
                DailyGoalCard(
                    khatmaCount   = selectedKhatma,
                    currentJuz    = currentJuz,
                    onRecordClick = {
                        scope.launch {
                            dashboardViewModel.completeJuz(currentJuz, currentKhatma)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ══ قائمة الأجزاء ════════════════════════
                JuzListSection(
                    khatmaCount     = selectedKhatma,
                    completedJuzSet = completedSet,
                    currentJuz      = (completedCount ?: 0) + 1,
                    onJuzToggle     = { globalJuz ->
                        val juzNum   = ((globalJuz - 1) % 30) + 1
                        val khatmaNum = ((globalJuz - 1) / 30) + 1
                        scope.launch {
                            val isCompleted = completedSet.contains(globalJuz)
                            if (!isCompleted) {
                                dashboardViewModel.completeJuz(juzNum, khatmaNum)
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}


// ── الفانوس مع نسبة التقدم ───────────────────────────
@Composable
fun LanternProgress(progress: Float) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 800),
        label = "lanternFill"
    )
    val percent = (animatedProgress * 100).toInt()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(width = 180.dp, height = 240.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.52f)
                .fillMaxHeight(animatedProgress.coerceAtLeast(0.02f))
                .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp, topStart = 20.dp, topEnd = 20.dp))
                .background(
                    brush = Brush.verticalGradient(
                        listOf(GreenLight.copy(alpha = 0.75f), GreenDark)
                    )
                )
        )

        Image(
            painter = painterResource(id = R.drawable.fanous),
            contentDescription = "فانوس رمضان",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = "%$percent",
                fontFamily = AlmaraiFont,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                color = WhiteColor
            )
            Text(
                text = "مكتمل",
                fontFamily = IbmPlexArabicFont,
                fontSize = 11.sp,
                color = WhiteColor.copy(alpha = 0.8f)
            )
        }
    }
}


// ── بطاقة الهدف اليومي ───────────────────────────────
@Composable
fun DailyGoalCard(
    khatmaCount: Int,
    currentJuz: Int,
    onRecordClick: () -> Unit
) {
    val dailyPages = khatmaCount * 20
    val startPage  = ((currentJuz - 1) * 20) + 1
    val endPage    = startPage + dailyPages - 1
    val juzInfo    = QuranData.juzList.getOrNull(currentJuz - 1)

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
                        .background(GreenLight.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "%${(currentJuz.toFloat() / 30f * 100).toInt()}",
                        color = GreenLight,
                        fontFamily = IbmPlexArabicFont,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(text = "الهدف اليومي", color = SubtitleColor, fontFamily = IbmPlexArabicFont, fontSize = 12.sp)
                    Text(text = "📅", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "عدد الصفحات يومياً: $dailyPages",
                fontFamily = AlmaraiFont,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = WhiteColor,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "صفحة $startPage - $endPage" +
                        if (juzInfo != null) " (${juzInfo.startSurah})" else "",
                color = SubtitleColor,
                fontFamily = IbmPlexArabicFont,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(WhiteColor.copy(alpha = 0.08f))
                        .border(1.dp, WhiteColor.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .clickable { }
                ) {
                    Text(text = "✏️", fontSize = 18.sp)
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(brush = Brush.horizontalGradient(listOf(GreenDark, GreenLight)))
                        .clickable { onRecordClick() }
                        .padding(vertical = 14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "✅", fontSize = 16.sp)
                        Text(
                            text = "سجل القراءة",
                            color = WhiteColor,
                            fontFamily = AlmaraiFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}


// ── قائمة الأجزاء ────────────────────────────────────
@Composable
fun JuzListSection(
    khatmaCount: Int,
    completedJuzSet: Set<Int>,
    currentJuz: Int,
    onJuzToggle: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(GreenLight.copy(alpha = 0.15f))
                        .border(1.dp, GreenLight.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = "بالجزء", color = GreenLight, fontFamily = IbmPlexArabicFont, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(WhiteColor.copy(alpha = 0.06f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = "بالسورة", color = SubtitleColor, fontFamily = IbmPlexArabicFont, fontSize = 12.sp)
                }
            }
            Text(text = "المتابعة", fontFamily = AlmaraiFont, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = WhiteColor)
        }

        Spacer(modifier = Modifier.height(12.dp))

        repeat(30 * khatmaCount) { index ->
            val juzNumber  = (index % 30) + 1
            val khatmaNum  = (index / 30) + 1
            val globalJuz  = index + 1
            val isCompleted = completedJuzSet.contains(globalJuz)
            val isCurrent  = globalJuz == currentJuz
            val juzInfo    = QuranData.juzList.getOrNull(juzNumber - 1)

            JuzItem(
                juzNumber   = juzNumber,
                khatmaNum   = if (khatmaCount > 1) khatmaNum else null,
                juzInfo     = juzInfo,
                isCompleted = isCompleted,
                isCurrent   = isCurrent,
                onToggle    = { onJuzToggle(globalJuz) }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


// ── عنصر جزء واحد ────────────────────────────────────
@Composable
fun JuzItem(
    juzNumber: Int,
    khatmaNum: Int?,
    juzInfo: com.example.ramadan.data.JuzInfo?,
    isCompleted: Boolean,
    isCurrent: Boolean,
    onToggle: () -> Unit
) {
    val borderColor = when {
        isCurrent   -> GreenLight
        isCompleted -> GreenDark.copy(alpha = 0.5f)
        else        -> WhiteColor.copy(alpha = 0.08f)
    }

    val arabicNumbers = listOf(
        "١","٢","٣","٤","٥","٦","٧","٨","٩","١٠",
        "١١","١٢","١٣","١٤","١٥","١٦","١٧","١٨","١٩","٢٠",
        "٢١","٢٢","٢٣","٢٤","٢٥","٢٦","٢٧","٢٨","٢٩","٣٠"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(if (isCurrent) GreenLight.copy(alpha = 0.07f) else WhiteColor.copy(alpha = 0.04f))
            .border(1.dp, borderColor, RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ── Checkbox ──────────────────────────────
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isCompleted) GreenLight.copy(alpha = 0.2f) else WhiteColor.copy(alpha = 0.06f))
                .border(1.5.dp, if (isCompleted) GreenLight else WhiteColor.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                .clickable { onToggle() }
        ) {
            if (isCompleted) {
                Text(text = "✓", color = GreenLight, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }

        // ── معلومات الجزء ──────────────────────────
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.weight(1f).padding(horizontal = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (isCurrent) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GreenLight.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = "الحالي", color = GreenLight, fontFamily = IbmPlexArabicFont, fontSize = 10.sp)
                    }
                } else if (isCompleted) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GreenDark.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = "تم", color = GreenDark, fontFamily = IbmPlexArabicFont, fontSize = 10.sp)
                    }
                }

                Text(
                    text = "الجزء ${arabicNumbers.getOrElse(juzNumber - 1) { juzNumber.toString() }}" +
                            if (khatmaNum != null) " — ختمة $khatmaNum" else "",
                    color = if (isCompleted || isCurrent) WhiteColor else WhiteColor.copy(alpha = 0.7f),
                    fontFamily = IbmPlexArabicFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = if (juzInfo != null)
                    "${juzInfo.startSurah} (${juzInfo.startAyah}) - ${juzInfo.endSurah} (${juzInfo.endAyah})"
                else "",
                color = SubtitleColor,
                fontFamily = IbmPlexArabicFont,
                fontSize = 11.sp
            )
        }

        // ── أول كلمات الجزء ───────────────────────
        Text(
            text = juzInfo?.firstWords ?: "",
            color = SubtitleColor.copy(alpha = 0.6f),
            fontFamily = IbmPlexArabicFont,
            fontSize = 13.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.width(80.dp)
        )
    }
}


// ── اختيار عدد الختمات ───────────────────────────────
@Composable
fun KhatmaSelector(
    selected: Int,
    onSelect: (Int) -> Unit
) {
    val options = listOf(1 to "ختمة واحدة", 2 to "ختمتان", 3 to "٣ ختمات")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(WhiteColor.copy(alpha = 0.06f))
            .border(1.dp, WhiteColor.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
            .padding(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            options.forEach { (value, label) ->
                val isSelected = selected == value
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) GreenLight else Color.Transparent)
                        .clickable { onSelect(value) }
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        text = label,
                        color = if (isSelected) WhiteColor else SubtitleColor,
                        fontFamily = IbmPlexArabicFont,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuranScreenPreview() {
    QuranScreen()
}