package com.example.ramadan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ramadan.ui.theme.AlmaraiFont
import com.example.ramadan.ui.theme.BgBottom
import com.example.ramadan.ui.theme.BgMid
import com.example.ramadan.ui.theme.BgTop
import com.example.ramadan.ui.theme.IbmPlexArabicFont
import com.example.ramadan.ui.theme.JourneyAccent
import com.example.ramadan.ui.theme.SubtitleColor
import com.example.ramadan.ui.theme.WhiteColor


@Composable
fun ProfileSetupScreen(onContinueClick: () -> Unit = {} ){
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
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Spacer(modifier = Modifier.height(48.dp))

            //Top bar + back button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                // arrow next on right
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(WhiteColor.copy(alpha = 0.1f))
                        .clickable {onContinueClick()}

                ){
                    Text(text = "←", color = WhiteColor, fontSize = 18.sp)
                }

                // شريط التقدم في المنتصف
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(50))
                        .background(WhiteColor.copy(alpha = 0.1f))

                ){
                    // الجزء المكتمل
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.66f) // الصفحة 2 من 3
                            .clip(RoundedCornerShape(50))
                            .background(JourneyAccent)

                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Header - title description

            Text(
                text = "إعداد الملف الشخصي",
                fontFamily = AlmaraiFont,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                color = WhiteColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "أكمل بياناتك للحصول على تجربة مخصصة",
                fontFamily = IbmPlexArabicFont,
                fontSize = 14.sp,
                color = SubtitleColor,
                textAlign = TextAlign.Center
            )

        }

    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileSetupScreenPreview() {
    ProfileSetupScreen()
}
