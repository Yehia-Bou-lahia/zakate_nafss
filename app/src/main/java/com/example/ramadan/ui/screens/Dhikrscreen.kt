package com.example.ramadan.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ramadan.ui.components.BottomNavBar
import com.example.ramadan.ui.theme.*
import java.util.Calendar

@Composable
fun DhikrScreen() {
    var tasbihCount by remember { mutableStateOf(0) }
    var tasbihGoal  by remember { mutableStateOf(100) }
    val progress = (tasbihCount.toFloat() / tasbihGoal.toFloat()).coerceIn(0f, 1f)

    // وقت الترحيب
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when {
        hour < 12 -> "صباح الخير"
        hour < 17 -> "مساء الخير"
        else      -> "السلام عليكم"
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedRoute = "dhikr",
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
                    // ── أيقونة الإعدادات ──────────────
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

                    // ── التحية ───────────────────────
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = greeting,
                            fontFamily = AlmaraiFont,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = WhiteColor
                        )
                        Text(
                            text = "١٢ رمضان ١٤٤٥ هـ 📅",
                            fontFamily = IbmPlexArabicFont,
                            fontSize = 12.sp,
                            color = SubtitleColor
                        )
                        Text(
                            text = "المغرب بعد ٢س ٥د 🌙",
                            fontFamily = IbmPlexArabicFont,
                            fontSize = 12.sp,
                            color = SubtitleColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ══ عداد التسبيح ════════════════════════
                Text(
                    text = "عداد التسبيح",
                    fontFamily = AlmaraiFont,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 26.sp,
                    color = WhiteColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "%${(progress * 100).toInt()}",
                        color = GreenLight,
                        fontFamily = IbmPlexArabicFont,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(SubtitleColor)
                    )
                    Text(
                        text = "الهدف: $tasbihGoal",
                        color = SubtitleColor,
                        fontFamily = IbmPlexArabicFont,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ── الدائرة الكبيرة ───────────────────
                TasbihCircle(
                    count    = tasbihCount,
                    progress = progress,
                    onClick  = { tasbihCount++ }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ── أزرار الأذكار السريعة ──────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    listOf("الله أكبر", "الحمد لله", "سبحان الله").forEach { dhikr ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(WhiteColor.copy(alpha = 0.06f))
                                .border(1.dp, GreenLight.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                .clickable { tasbihCount++ }
                                .padding(vertical = 12.dp)
                        ) {
                            Text(
                                text = dhikr,
                                color = WhiteColor,
                                fontFamily = IbmPlexArabicFont,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ── أزرار التحكم ──────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // إعادة
                    TasbihControlButton(
                        emoji = "↺",
                        label = "إعادة",
                        onClick = { tasbihCount = 0 }
                    )
                    // حفظ
                    TasbihControlButton(
                        emoji = "✓",
                        label = "حفظ",
                        isActive = true,
                        onClick = { }
                    )
                    // هدف
                    TasbihControlButton(
                        emoji = "🎯",
                        label = "هدف",
                        onClick = { tasbihGoal = if (tasbihGoal == 100) 33 else if (tasbihGoal == 33) 99 else 100 }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}


// ── دائرة عداد التسبيح ───────────────────────────────
@Composable
fun TasbihCircle(
    count: Int,
    progress: Float,
    onClick: () -> Unit
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(300),
        label = "tasbih"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(220.dp)
            .clickable { onClick() }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 12.dp.toPx()
            val inset = strokeWidth / 2

            // ── الدائرة الخلفية ───────────────────
            drawArc(
                color = Color(0xFF1A3A1A),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )

            // ── الدائرة الأمامية (التقدم) ─────────
            drawArc(
                brush = Brush.sweepGradient(
                    listOf(GreenDark, GreenLight, GreenLight)
                ),
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // ── خلفية الدائرة ─────────────────────────
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(190.dp)
                .clip(CircleShape)
                .background(Color(0xFF0D1F0D))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = count.toString(),
                    fontFamily = AlmaraiFont,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 64.sp,
                    color = WhiteColor
                )
                Text(
                    text = "العدد",
                    fontFamily = IbmPlexArabicFont,
                    fontSize = 14.sp,
                    color = SubtitleColor
                )
            }
        }
    }
}


// ── زر تحكم التسبيح ──────────────────────────────────
@Composable
fun TasbihControlButton(
    emoji: String,
    label: String,
    isActive: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(
                    if (isActive) GreenLight.copy(alpha = 0.2f)
                    else WhiteColor.copy(alpha = 0.06f)
                )
                .border(
                    1.dp,
                    if (isActive) GreenLight.copy(alpha = 0.5f)
                    else WhiteColor.copy(alpha = 0.1f),
                    CircleShape
                )
        ) {
            Text(
                text = emoji,
                fontSize = 20.sp,
                color = if (isActive) GreenLight else WhiteColor
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            color = if (isActive) GreenLight else SubtitleColor,
            fontFamily = IbmPlexArabicFont,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DhikrScreenPreview() {
    DhikrScreen()
}