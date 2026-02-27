package com.example.ramadan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ramadan.R
import com.example.ramadan.ui.theme.AlmaraiFont
import com.example.ramadan.ui.theme.IbmPlexArabicFont
import com.example.ramadan.ui.theme.JourneyAccent
import com.example.ramadan.ui.theme.JourneyBgBottom
import com.example.ramadan.ui.theme.JourneyBgMid
import com.example.ramadan.ui.theme.JourneyBgTop
import com.example.ramadan.ui.theme.JourneyBlue
import com.example.ramadan.ui.theme.JourneyCard
import com.example.ramadan.ui.theme.JourneyGreen
import com.example.ramadan.ui.theme.JourneyIconBg
import com.example.ramadan.ui.theme.SubtitleColor
import com.example.ramadan.ui.theme.WhiteColor

@Composable
fun OnboardingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to JourneyBgTop,
                        0.6f to JourneyBgMid,
                        1.0f to JourneyBgBottom
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.End
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // arrow back
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(WhiteColor.copy(alpha = 0.1f))
                    .align(Alignment.Start)
            ) {
                Text(
                    text = "←",
                    color = WhiteColor,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // main title
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = WhiteColor)) {
                        append("حدد ")
                    }
                    withStyle(style = SpanStyle(color = JourneyAccent)) {
                        append("مسار رحلتك")
                    }
                },
                fontFamily = AlmaraiFont,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Title description
            Text(
                text = "اختر العادات التي ترغب في الالتزام بها خلال هذا الشهر الفضيل.",
                fontFamily = IbmPlexArabicFont,
                fontSize = 14.sp,
                color = SubtitleColor,
                textAlign = TextAlign.End,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // cards state
            val selectedItems = remember { mutableStateListOf<String>() }

            // cards
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HabitCard(
                        id = "quran",
                        title = "ختم القرآن",
                        subtitle = "إتمام قراءة الكتاب الكريم",
                        icon = R.drawable.quran,
                        isSelected = selectedItems.contains("quran"),
                        onToggle = {
                            if (selectedItems.contains("quran")) selectedItems.remove("quran")
                            else selectedItems.add("quran")
                        },
                        modifier = Modifier.weight(1f)
                    )
                    HabitCard(
                        id = "adkar",
                        title = "الأذكار اليومية",
                        subtitle = "أذكار الصباح و المساء",
                        icon = R.drawable.adkare,
                        isSelected = selectedItems.contains("adkar"),
                        onToggle = {
                            if (selectedItems.contains("adkar")) selectedItems.remove("adkar")
                            else selectedItems.add("adkar")
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HabitCard(
                        id = "tarawih",
                        title = "التراويح",
                        subtitle = "الصلوات الليلية",
                        icon = R.drawable.mosque,
                        isSelected = selectedItems.contains("tarawih"),
                        onToggle = {
                            if(selectedItems.contains("tarawih")) selectedItems.remove("tarawih")
                            else selectedItems.add("tarawih")
                        },
                        modifier = Modifier.weight(1f)
                    )
                    HabitCard(
                        id = "sadaka",
                        title = "الصدقة",
                        subtitle = "الصدقات و الزكاة",
                        icon = R.drawable.sadaka,
                        isSelected = selectedItems.contains("sadaka"),
                        onToggle = {
                            if(selectedItems.contains("sadaka")) selectedItems.remove("sadaka")
                            else selectedItems.add("sadaka")
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // كثافة العبادات اليومية
            var sliderValue by remember { mutableStateOf(0.5f) }

            val intensityLable = when {
                sliderValue < 0.33f -> "مكثفة"
                sliderValue < 0.66f -> "متوازنة"
                else -> "يسيرة"
            }
            //time
            val estimatedMinutes = when {
                sliderValue < 0.33f -> 75
                sliderValue < 0.66f -> 45
                else                -> 20
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .border(1.dp , JourneyAccent, RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ){
                    Text(
                        text = intensityLable,
                        color = JourneyAccent,
                        fontFamily = IbmPlexArabicFont,
                        fontSize = 12.sp
                    )
                }

                Text(
                    text = "كثافة العبادة اليومية",
                    color = WhiteColor,
                    fontFamily = AlmaraiFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // information Box
            androidx.compose.material3.Slider(
                value = 1f - sliderValue,
                onValueChange = { sliderValue = 1f - it },
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.SliderDefaults.colors(
                    thumbColor = JourneyAccent,
                    activeTrackColor = JourneyAccent,
                    inactiveTrackColor = JourneyCard
                )
            )
            // naming args slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "يسيرة",   color = SubtitleColor, fontSize = 11.sp, fontFamily = IbmPlexArabicFont)
                Text(text = "متوازنة", color = SubtitleColor, fontSize = 11.sp, fontFamily = IbmPlexArabicFont)
                Text(
                    text = "مكثفة",
                    color = if (sliderValue < 0.33f) JourneyAccent else SubtitleColor,
                    fontSize = 11.sp,
                    fontFamily = IbmPlexArabicFont
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Infomation Box
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(JourneyCard)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(JourneyBlue)
                ) {
                    Text(
                        text = "i",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Text(
                    text = "بناءً على إعداداتك يُتوقع تخصيص حوالي $estimatedMinutes دقيقة للعبادات اليومية.",
                    color = WhiteColor,
                    fontFamily = IbmPlexArabicFont,
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            // زر المتابعة
            androidx.compose.material3.Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(50),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = JourneyBlue
                )
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "←",
                        color = WhiteColor,
                        fontSize = 24.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "متابعة",
                        color = WhiteColor,
                        fontFamily = AlmaraiFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun HabitCard(
    id: String,
    title: String,
    subtitle: String,
    icon: Int,
    isSelected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .then(
                if (isSelected) Modifier.shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = JourneyAccent,
                    spotColor = JourneyAccent
                ) else Modifier
            )
            .clip(RoundedCornerShape(16.dp))
            .background(JourneyCard)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) JourneyAccent else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onToggle() }
            .padding(16.dp)
    ) {
        // علامة الصح أو الدائرة الفارغة
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected) JourneyGreen
                    else Color.Transparent
                )
                .border(
                    width = if (isSelected) 0.dp else 2.dp,
                    color = if (isSelected) Color.Transparent else Color.White.copy(alpha = 0.4f),
                    shape = CircleShape
                )
                .align(Alignment.TopStart)
        ) {
            if (isSelected) {
                Image(
                    painter = painterResource(id = R.drawable.checked),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // الأيقونة والنصوص
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (isSelected) JourneyBlue else JourneyIconBg
                    )
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = title,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                fontFamily = AlmaraiFont,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = WhiteColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                fontFamily = IbmPlexArabicFont,
                fontSize = 10.sp,
                color = SubtitleColor,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 14.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen()
}