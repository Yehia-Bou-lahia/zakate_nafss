package com.example.ramadan.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ramadan.R
import com.example.ramadan.ui.theme.GoldColor
import com.example.ramadan.ui.theme.GreenDark
import com.example.ramadan.ui.theme.GreenLight
import com.example.ramadan.ui.theme.IbmPlexArabicFont
import com.example.ramadan.ui.theme.SubtitleColor
import com.example.ramadan.ui.theme.WhiteColor

sealed class BottomNavItem(val route: String, val icon: Int?, val label: String) {
    object Home    : BottomNavItem("quran",     R.drawable.home,    "الرئيسية")
    object Stats   : BottomNavItem("dashboard", R.drawable.statics, "الإحصائيات")
    object Add     : BottomNavItem("add",       null,               "")
    object Dhikr   : BottomNavItem("dhikr",     R.drawable.dhikr,    "الذكر")
    object Profile : BottomNavItem("profile",   R.drawable.user,    "الملف")
}

@Composable
fun BottomNavBar(
    selectedRoute: String = "quran",
    onItemSelected: (String) -> Unit = {}
) {
    // RTL: الرئيسية في اليمين — الذكر في اليسار
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Stats,
        BottomNavItem.Add,
        BottomNavItem.Dhikr,
        BottomNavItem.Profile
    ).reversed()

    val barColor = Color(0xFF0D1F0D)

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(barColor)
                .padding(top = 28.dp, bottom = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    if (item is BottomNavItem.Add) {
                        Box(modifier = Modifier.size(64.dp))
                        return@forEach
                    }

                    val isSelected = item.route == selectedRoute

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) GoldColor.copy(alpha = 0.15f)
                                else Color.Transparent
                            )
                            .clickable { onItemSelected(item.route) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon!!),
                            contentDescription = item.label,
                            tint = if (isSelected) GoldColor else SubtitleColor,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.label,
                            color = if (isSelected) GoldColor else SubtitleColor,
                            fontFamily = IbmPlexArabicFont,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        // ── زر الإضافة الطافي ─────────────────────
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-8).dp)
                .shadow(
                    elevation = 24.dp,
                    shape = CircleShape,
                    ambientColor = GreenLight,
                    spotColor = GreenLight
                )
                .size(60.dp)
                .clip(CircleShape)
                .border(3.dp, barColor, CircleShape)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(GreenLight, GreenDark)
                    )
                )
                .clickable { onItemSelected("add") }
        ) {
            Text(
                text = "+",
                color = WhiteColor,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp
            )
        }
    }
}