package com.example.ramadan.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ramadan.ui.theme.AlmaraiFont
import com.example.ramadan.ui.theme.BgBottom
import com.example.ramadan.ui.theme.BgMid
import com.example.ramadan.ui.theme.BgTop
import com.example.ramadan.ui.theme.GoldColor
import com.example.ramadan.ui.theme.IbmPlexArabicFont
import com.example.ramadan.ui.theme.JourneyAccent
import com.example.ramadan.ui.theme.SubtitleColor
import com.example.ramadan.ui.theme.WhiteColor
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.platform.LocalContext
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.LocationServices
import java.util.Locale
import android.location.Geocoder
import androidx.compose.foundation.border
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.painterResource
import com.example.ramadan.R

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
                .verticalScroll(rememberScrollState())
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

            Spacer(modifier = Modifier.height(32.dp))
            // profile picture
            // remember لحفظ حالة الصورة المختارة
            var imageUri by remember { mutableStateOf<Uri?>(null) }
            /// launcher for open photos
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri -> imageUri = uri }
            // circle + add icon
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally) // center on column
                    .clickable{ launcher.launch("image/*")}
            ){
                // main circle
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(WhiteColor.copy(alpha = 0.1f))
                ){
                    if (imageUri != null) {
                        // if user choose photo
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "profile photo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }
                }

                // add icon + gold in corner
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(GoldColor)
                        .align(Alignment.BottomStart)

                ){
                    Text(text = "+" ,color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            // ── state للاسم والموقع ───────────────────────
            var fullName by remember { mutableStateOf("") }
            var locationText by remember { mutableStateOf("") }
            var isLoadingLocation by remember { mutableStateOf(false) }

            val context = LocalContext.current

// launcher لطلب إذن الموقع
            val locationPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    isLoadingLocation = true
                    val fusedClient = LocationServices.getFusedLocationProviderClient(context)
                    try {
                        fusedClient.lastLocation.addOnSuccessListener { location ->
                            if (location != null) {
                                val geocoder = Geocoder(context, Locale("ar"))
                                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                                if (!addresses.isNullOrEmpty()) {
                                    val address = addresses[0]
                                    locationText = "${address.locality}, ${address.countryName}"
                                }
                            }
                            isLoadingLocation = false
                        }
                    } catch (e: SecurityException) {
                        isLoadingLocation = false
                    }
                }
            }

// ══ حقل الاسم الكامل ══════════════════════════
            Text(
                text = "الاسم الكامل",
                color = WhiteColor,
                fontFamily = AlmaraiFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(8.dp))

// ── TextField مخصص ───────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(WhiteColor.copy(alpha = 0.08f))
                    .border(1.dp, WhiteColor.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (fullName.isEmpty()) {
                    Text(
                        text = "مثال: يحيى ",
                        color = SubtitleColor,
                        fontFamily = IbmPlexArabicFont,
                        fontSize = 14.sp
                    )
                }
                BasicTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = WhiteColor,
                        fontFamily = IbmPlexArabicFont,
                        fontSize = 14.sp,
                        textAlign = TextAlign.End
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

// ══ حقل الموقع الجغرافي ══════════════════════
            Text(
                text = "الموقع الجغرافي",
                color = WhiteColor,
                fontFamily = AlmaraiFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // ── زر تحديد الموقع ──────────────────────
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(WhiteColor.copy(alpha = 0.08f))
                        .border(1.dp, WhiteColor.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .clickable {
                            locationPermissionLauncher.launch(
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        }
                ) {
                    if (isLoadingLocation) {
                        androidx.compose.material3.CircularProgressIndicator(
                            color = WhiteColor,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.gps),
                            contentDescription = "تحديد الموقع",
                            modifier = Modifier.size(28.dp)
                        )                    }
                }

                // ── حقل النص ─────────────────────────────
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(WhiteColor.copy(alpha = 0.08f))
                        .border(1.dp, WhiteColor.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    if (locationText.isEmpty()) {
                        Text(
                            text = "المدينة، الدولة",
                            color = SubtitleColor,
                            fontFamily = IbmPlexArabicFont,
                            fontSize = 14.sp
                        )
                    }
                    BasicTextField(
                        value = locationText,
                        onValueChange = { locationText = it },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            color = WhiteColor,
                            fontFamily = IbmPlexArabicFont,
                            fontSize = 14.sp,
                            textAlign = TextAlign.End
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "يستخدم لتحديد أوقات الصلاة بدقة",
                color = SubtitleColor,
                fontFamily = IbmPlexArabicFont,
                fontSize = 11.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(32.dp))

            // المداهب الفقهية
            // state لحفظ المدهب
            var selectedMadhab by remember {mutableStateOf("")}
            var isMadhabExpanded by remember { mutableStateOf(false) }

            val madhabs = listOf("الحنبلي","الشافعي","المالكي","الحنفي")

            Text(
                text = "المدهب الفقهي",
                color = WhiteColor,
                fontFamily = AlmaraiFont,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            // ── Dropdown المذهب ───────────────────────────
            Box(modifier = Modifier.fillMaxWidth()) {
                // زر الاختيار
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(WhiteColor.copy(alpha = 0.08f))
                        .border(1.dp, WhiteColor.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .clickable { isMadhabExpanded = !isMadhabExpanded }
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // سهم للأسفل
                        Text(
                            text = if (isMadhabExpanded) "▲" else "▼",
                            color = SubtitleColor,
                            fontSize = 12.sp
                        )

                        // النص المختار أو placeholder
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (selectedMadhab.isEmpty()) "اختر المذهب" else selectedMadhab,
                                color = if (selectedMadhab.isEmpty()) SubtitleColor else WhiteColor,
                                fontFamily = IbmPlexArabicFont,
                                fontSize = 14.sp
                            )
                            Image(
                                painter = painterResource(id = R.drawable.mosque),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // ── قائمة المذاهب ─────────────────────────
                if (isMadhabExpanded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 56.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1E2D4F))
                            .border(1.dp, WhiteColor.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                    ) {
                        madhabs.forEach { madhab ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedMadhab = madhab
                                        isMadhabExpanded = false
                                    }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(
                                    text = madhab,
                                    color = if (selectedMadhab == madhab) GoldColor else WhiteColor,
                                    fontFamily = IbmPlexArabicFont,
                                    fontSize = 14.sp
                                )
                            }
                            // فاصل بين الخيارات إلا الأخير
                            if (madhab != madhabs.last()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(WhiteColor.copy(alpha = 0.08f))
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

// ══ زر حفظ ومتابعة ════════════════════════════
            androidx.compose.material3.Button(
                onClick = { onContinueClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(50),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = GoldColor
                )
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "→",
                        color = Color.Black,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = "حفظ ومتابعة",
                        color = Color.Black,
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
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileSetupScreenPreview() {
    ProfileSetupScreen()
}
