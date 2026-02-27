package com.example.ramadan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ramadan.R
import com.example.ramadan.ui.theme.*

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {

    Box(modifier = Modifier.fillMaxSize()) {

        // ── 1. الخلفية ────────────────────────────────────
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
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(48.dp))

            // ── 2. الوهج + الفانوس + الإطار الذهبي ──────────
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(320.dp)
            ) {
                // الوهج خلف الفانوس
                Image(
                    painter = painterResource(id = R.drawable.glow),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // الإطار الذهبي — دائرة ذهبية خلف الفانوس مباشرة
                Box(
                    modifier = Modifier
                        .size(308.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    GoldColor,                    // ذهبي فاتح فوق
                                    GoldColor.copy(alpha = 0.3f)  // ذهبي شفاف أسفل
                                )
                            ),
                            shape = CircleShape
                        )
                )

                // الفانوس فوق الإطار
                Image(
                    painter = painterResource(id = R.drawable.lantern),
                    contentDescription = "فانوس رمضان",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(300.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── 3. النجمة الذهبية ─────────────────────────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 80.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(GoldColor.copy(alpha = 0.6f))
                )
                Text(
                    text = "★",
                    color = GoldColor,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(GoldColor.copy(alpha = 0.6f))
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── 4. العنوان مع الظل الذهبي ─────────────────────
            // Box يحتوي على الظل خلف النص
            Box(
                contentAlignment = Alignment.Center
            ) {
                // الظل الذهبي — blur خلف النص
                Box(
                    modifier = Modifier
                        .width(280.dp)
                        .height(90.dp)
                        .blur(radius = 40.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    GoldColor.copy(alpha = 0.25f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // النصان فوق الظل
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "مرحباً بك في",
                        fontFamily = AlmaraiFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = WhiteColor,
                        textAlign = TextAlign.Center,
                        style = TextStyle(textDirection = TextDirection.Rtl)
                    )
                    Text(
                        text = "رفيق رمضان",
                        fontFamily = AlmaraiFont,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 36.sp,
                        color = GoldColor,
                        textAlign = TextAlign.Center,
                        style = TextStyle(textDirection = TextDirection.Rtl)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── 5. النص الوصفي ────────────────────────────────
            Text(
                text = "رحلة روحانية للنمو والعبادة، نرافقك فيها خطوة بخطوة.",
                fontFamily = IbmPlexArabicFont,
                fontSize = 16.sp,
                color = SubtitleColor,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp,
                modifier = Modifier.padding(horizontal = 36.dp),
                style = TextStyle(textDirection = TextDirection.Rtl)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ── 6. التاقات ────────────────────────────────────
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(horizontal = 12.dp)
                ){
                    FeatureTag("ختم القرآن")
                    FeatureTag("أذكار يومية")
                    FeatureTag("صلاة التراويح")
                    FeatureTag("الصدقات")

                }

            }

            Spacer(modifier = Modifier.weight(1f))

            // ── 7. زر ابدأ الآن ───────────────────────────────
            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = GoldColor)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "←",
                        color = Color.Black,
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "ابدأ الآن",
                        color = Color.Black,
                        fontFamily = AlmaraiFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.Center)
                        //textAlign = TextAlign.Center
                    )
                }

            }

            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}

@Composable
fun FeatureTag(label: String) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = TagBorderColor,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = TagTextColor,
            fontFamily = IbmPlexArabicFont,
            fontSize = 11.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(onStartClick = {})
}