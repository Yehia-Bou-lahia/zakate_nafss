package com.example.ramadan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import com.example.ramadan.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ramadan.ui.theme.AlmaraiFont
import com.example.ramadan.ui.theme.BgBottom
import com.example.ramadan.ui.theme.BgMid
import com.example.ramadan.ui.theme.BgTop
import com.example.ramadan.ui.theme.GoldColor
import com.example.ramadan.ui.theme.IbmPlexArabicFont
import com.example.ramadan.ui.theme.SubtitleColor
import com.example.ramadan.ui.theme.WhiteColor
import java.util.Calendar


// ── تحويل التاريخ الميلادي للهجري ──────────────────────
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
fun DashboardScreen() {
    val (hijriDay, hijriMonth, hijriYear) = remember { getHijriDate() }
    val todayArabic = remember { getTodayArabic() }

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
                // ── أيقونة القائمة ────────────────────────
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

                // ── صورة الملف الشخصي ─────────────────────
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
                Text(
                    text = "استمر في التقدم",
                    color = SubtitleColor,
                    fontFamily = IbmPlexArabicFont,
                    fontSize = 13.sp
                )
                Text(text = " • ", color = SubtitleColor, fontSize = 13.sp)
                Text(
                    text = "$hijriDay",
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
                    // ── الهلال + السنة الهجرية ───────────
                    Column(horizontalAlignment = Alignment.Start) {
                        Image(
                            painter = painterResource(id = R.drawable.hilaldb),
                            contentDescription = null,
                            modifier = Modifier
                                .size(78.dp)
                                .padding(4.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = " هـ$hijriYear",
                            color = SubtitleColor,
                            fontFamily = IbmPlexArabicFont,
                            fontSize = 12.sp
                        )
                    }

                    // ── اليوم + الشهر ────────────────────
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = todayArabic,
                            color = SubtitleColor,
                            fontFamily = IbmPlexArabicFont,
                            fontSize = 13.sp
                        )
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
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0x00C9A84C), GoldColor)
                                    )
                                )
                        )
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