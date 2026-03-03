package com.example.ramadan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ramadan.ui.theme.AlmaraiFont
import com.example.ramadan.ui.theme.IbmPlexArabicFont
import com.example.ramadan.ui.theme.PrayerBgBottom
import com.example.ramadan.ui.theme.PrayerBgMid
import com.example.ramadan.ui.theme.PrayerBgTop
import com.example.ramadan.ui.theme.SubtitleColor
import com.example.ramadan.ui.theme.WhiteColor


@Composable
fun PrayerNotificationScreen(onStartClick: () -> Unit = {}){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to PrayerBgTop,
                        0.5f to PrayerBgMid,
                        1.0f to PrayerBgBottom
                    )
                )
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.End
        ){
            Spacer(modifier = Modifier.height(64.dp))
            // ══ العنوان الرئيسي ════════════════════════
            Text(
                text = "ابقَ على اتصال بصلاتك",
                fontFamily = AlmaraiFont,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                color = WhiteColor,
                textAlign = TextAlign.Center,
                lineHeight = 48.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ══ الوصف ══════════════════════════════════
            Text(
                text = "نحن نحسب الأوقات الدقيقة بناءً على موقعك الجغرافي",
                fontFamily = IbmPlexArabicFont,
                fontSize = 14.sp,
                color = SubtitleColor,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrayerNotificationScreenPreview() {
    PrayerNotificationScreen()
}