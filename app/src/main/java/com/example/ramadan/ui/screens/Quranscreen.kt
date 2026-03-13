package com.example.ramadan.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
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
    onNavigate: (String) -> Unit = {},
    dashboardViewModel: DashboardViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    var selectedKhatma by remember { mutableStateOf(1) }
    var showKhatmaDialog by remember { mutableStateOf(false) }

    val allProgress    by dashboardViewModel.allQuranProgress.collectAsState()
    val completedCount by dashboardViewModel.completedJuzCount.collectAsState()

    val completed     = completedCount ?: 0
    val totalJuz      = selectedKhatma * 30
    val progress      = dashboardViewModel.getLanternProgress(selectedKhatma, completed)
    val currentJuz    = dashboardViewModel.getCurrentJuz(selectedKhatma, completed)
    val currentKhatma = dashboardViewModel.getCurrentKhatma(completed)

    // ── تهيئة الأجزاء عند أول تحميل وعند تغيير الختمات ──
    LaunchedEffect(selectedKhatma) {
        dashboardViewModel.initQuranProgress(selectedKhatma)
    }

    val completedSet = allProgress
        .filter { it.isCompleted }
        .map { it.juzNumber + ((it.khatmaNumber - 1) * 30) }
        .toSet()

    if (showKhatmaDialog) {
        AlertDialog(
            onDismissRequest = { showKhatmaDialog = false },
            containerColor = Color(0xFF0D1F0D),
            title = {
                Text(
                    text = "🎉 أتممت الختمة!",
                    fontFamily = AlmaraiFont,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = GoldColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = "ستبدأ دورة جديدة لختم القرآن الكريم.\nهل تريد المتابعة؟",
                    fontFamily = IbmPlexArabicFont,
                    fontSize = 14.sp,
                    color = WhiteColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showKhatmaDialog = false
                    scope.launch {
                        dashboardViewModel.resetQuranProgress(selectedKhatma)
                    }
                }) {
                    Text(text = "تأكيد", color = GreenLight, fontFamily = AlmaraiFont, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showKhatmaDialog = false }) {
                    Text(text = "إلغاء", color = SubtitleColor, fontFamily = IbmPlexArabicFont)
                }
            }
        )
    }

    Scaffold(
        bottomBar = { BottomNavBar(selectedRoute = "quran", onItemSelected = { }) },
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

                KhatmaSelector(
                    selected = selectedKhatma,
                    onSelect = { newKhatma ->
                        selectedKhatma = newKhatma
                        scope.launch { dashboardViewModel.initQuranProgress(newKhatma) }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                LanternProgress(progress = progress)

                Spacer(modifier = Modifier.height(20.dp))

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
                    text = "متقدم • ${(30 - currentJuz).coerceAtLeast(0)} أيام متبقية",
                    fontFamily = IbmPlexArabicFont,
                    fontSize = 13.sp,
                    color = SubtitleColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                DailyGoalCard(
                    khatmaCount    = selectedKhatma,
                    currentJuz     = currentJuz,
                    completedCount = completed,
                    totalJuz       = totalJuz,
                    onRecordClick  = {
                        scope.launch {
                            dashboardViewModel.completeJuz(currentJuz, currentKhatma)
                            if (currentJuz == 30) showKhatmaDialog = true
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                JuzListSection(
                    khatmaCount     = selectedKhatma,
                    completedJuzSet = completedSet,
                    currentJuz      = completed + 1,
                    onJuzToggle     = { globalJuz ->
                        val juzNum    = ((globalJuz - 1) % 30) + 1
                        val khatmaNum = ((globalJuz - 1) / 30) + 1
                        scope.launch {
                            if (!completedSet.contains(globalJuz)) {
                                dashboardViewModel.completeJuz(juzNum, khatmaNum)
                                if (juzNum == 30) showKhatmaDialog = true
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}


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
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val padH = w * 0.22f
            val padT = h * 0.18f
            val padB = h * 0.10f
            val bodyLeft   = padH
            val bodyRight  = w - padH
            val bodyTop    = padT
            val bodyBottom = h - padB
            val bodyHeight = bodyBottom - bodyTop
            val fillHeight = bodyHeight * animatedProgress
            val fillTop    = (bodyBottom - fillHeight).coerceAtLeast(bodyTop)

            val lanternPath = Path().apply {
                addOval(Rect(left = bodyLeft, top = bodyTop, right = bodyRight, bottom = bodyBottom))
            }
            val fillPath = Path().apply {
                moveTo(bodyLeft - 1f, fillTop)
                lineTo(bodyRight + 1f, fillTop)
                lineTo(bodyRight + 1f, bodyBottom + 1f)
                lineTo(bodyLeft - 1f, bodyBottom + 1f)
                close()
            }

            clipPath(lanternPath) {
                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(GreenLight.copy(alpha = 0.85f), GreenDark),
                        startY = fillTop,
                        endY   = bodyBottom
                    )
                )
            }
        }

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
                .padding(bottom = 30.dp)
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


@Composable
fun DailyGoalCard(
    khatmaCount: Int,
    currentJuz: Int,
    completedCount: Int,
    totalJuz: Int,
    onRecordClick: () -> Unit
) {
    val dailyPages  = khatmaCount * 20
    val startPage   = ((currentJuz - 1) * 20) + 1
    val endPage     = startPage + dailyPages - 1
    val juzInfo     = QuranData.juzList.getOrNull(currentJuz - 1)
    val progressPct = if (totalJuz == 0) 0 else (completedCount * 100 / totalJuz)

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
                        text = "%$progressPct",
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
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = null,
                        tint = SubtitleColor,
                        modifier = Modifier.size(16.dp)
                    )
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
                    Icon(
                        painter = painterResource(id = R.drawable.hillal),
                        contentDescription = "تعديل",
                        tint = SubtitleColor,
                        modifier = Modifier.size(20.dp)
                    )
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
                        Icon(
                            painter = painterResource(id = R.drawable.checked),
                            contentDescription = null,
                            tint = WhiteColor,
                            modifier = Modifier.size(18.dp)
                        )
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
            val juzNumber   = (index % 30) + 1
            val khatmaNum   = (index / 30) + 1
            val globalJuz   = index + 1
            val isCompleted = completedJuzSet.contains(globalJuz)
            val isCurrent   = globalJuz == currentJuz
            val juzInfo     = QuranData.juzList.getOrNull(juzNumber - 1)

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