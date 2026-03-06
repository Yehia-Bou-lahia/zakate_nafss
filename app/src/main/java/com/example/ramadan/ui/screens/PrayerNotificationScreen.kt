package com.example.ramadan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import com.example.ramadan.R
import com.example.ramadan.ui.theme.AlmaraiFont
import com.example.ramadan.ui.theme.GoldColor
import com.example.ramadan.ui.theme.IbmPlexArabicFont
import com.example.ramadan.ui.theme.JourneyGreen
import com.example.ramadan.ui.theme.PrayerBgBottom
import com.example.ramadan.ui.theme.PrayerBgMid
import com.example.ramadan.ui.theme.PrayerBgTop
import com.example.ramadan.ui.theme.SubtitleColor
import com.example.ramadan.ui.theme.WhiteColor
import com.google.android.gms.location.LocationServices
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.location.Geocoder


@Composable
fun PrayerNotificationScreen(onStartClick: () -> Unit = {}) {

    // ══ state للموقع وأوقات الصلاة ════════════════
    var userLocation by remember { mutableStateOf<GeoPoint?>(null) }
    var prayerTimes by remember { mutableStateOf<PrayerTimes?>(value = null) }
    val context = LocalContext.current

    // ── حساب أوقات الصلاة عند تحميل الصفحة ──────
    LaunchedEffect(Unit) {
        try {
            val fusedClient = LocationServices.getFusedLocationProviderClient(context)
            fusedClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    userLocation = GeoPoint(location.latitude, location.longitude)
                    val coordinates = Coordinates(location.latitude, location.longitude)
                    val params = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
                    val dateComponents = DateComponents.from(Date())
                    prayerTimes = PrayerTimes(coordinates, dateComponents, params)
                }
            }
        } catch (e: SecurityException) {
            // الإذن غير ممنوح
        }
    }

    // ── helper لتنسيق الوقت ───────────────────────
    fun formatTime(date: Date?): String {
        if (date == null) return "--:--"
        val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        return sdf.format(date)
    }

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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.End
        ) {

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

            Spacer(modifier = Modifier.height(24.dp))

            // ══ الخريطة ════════════════════════════════
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                // ── OSMDroid MapView ──────────────────
                AndroidView(
                    factory = { ctx ->
                        MapView(ctx).apply {
                            setTileSource(TileSourceFactory.MAPNIK)
                            setMultiTouchControls(true)
                            controller.setZoom(13.0)
                            userLocation?.let {
                                controller.setCenter(it)
                                overlays.add(Marker(this).apply {
                                    position = it
                                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                })
                            }
                        }
                    },
                    update = { mapView ->
                        userLocation?.let { geoPoint ->
                            mapView.controller.setCenter(geoPoint)
                            mapView.overlays.clear()
                            mapView.overlays.add(Marker(mapView).apply {
                                position = geoPoint
                                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            })
                            mapView.invalidate()
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // ── بطاقة الموقع الحالي في الأسفل ────
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(Color(0xCC1A1208))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gps),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "الموقع الحالي", color = SubtitleColor, fontSize = 11.sp, fontFamily = IbmPlexArabicFont)
                            Text(text = "جاري التحديد...", color = WhiteColor, fontSize = 14.sp, fontFamily = AlmaraiFont, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ══ عنوان التنبيهات ════════════════════════
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "التنبيهات",
                    color = WhiteColor,
                    fontFamily = AlmaraiFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.size(8.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(GoldColor)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ══ state للتنبيهات ════════════════════════
            var fajrEnabled    by remember { mutableStateOf(true) }
            var dhuhrEnabled   by remember { mutableStateOf(true) }
            var asrEnabled     by remember { mutableStateOf(true) }
            var maghribEnabled by remember { mutableStateOf(true) }
            var ishaEnabled    by remember { mutableStateOf(false) }

            // ── قائمة الصلوات ─────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                PrayerCard("الفجر",  formatTime(prayerTimes?.fajr),    R.drawable.fadjer,      fajrEnabled)    { fajrEnabled = it }
                PrayerCard("الظهر",  formatTime(prayerTimes?.dhuhr),   R.drawable.duhr,       dhuhrEnabled)   { dhuhrEnabled = it }
                PrayerCard("العصر",  formatTime(prayerTimes?.asr),     R.drawable.asar, asrEnabled)     { asrEnabled = it }
                PrayerCard("المغرب", formatTime(prayerTimes?.maghrib), R.drawable.maghreb,   maghribEnabled) { maghribEnabled = it }
                PrayerCard("العشاء", formatTime(prayerTimes?.isha),    R.drawable.echa,        ishaEnabled)    { ishaEnabled = it }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ══ زر ابدأ رحلتي الرمضانية ══════════════
            androidx.compose.material3.Button(
                onClick = { onStartClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(
                        elevation = 30.dp,
                        shape = RoundedCornerShape(50),
                        ambientColor = GoldColor,
                        spotColor = GoldColor
                    ),
                shape = RoundedCornerShape(50),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = GoldColor
                )
            ) {
                    Text(
                        text = "ابدأ رحلت ",
                        color = Color.Black,
                        fontFamily = AlmaraiFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                        //modifier = Modifier.align(Alignment.Center)
                    )

            }

            Spacer(modifier = Modifier.height(32.dp))

        } // ← إغلاق Column
    } // ← إغلاق Box
} // ← إغلاق PrayerNotificationScreen


// ── بطاقة صلاة واحدة ─────────────────────────
@Composable
fun PrayerCard(
    name: String,
    time: String,
    icon: Int,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(40.dp))
            .background(Color(0xFF1A1208).copy(alpha = 0.6f))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ── زر التفعيل على اليسار ────────────────
        androidx.compose.material3.Switch(
            checked = enabled,
            onCheckedChange = onToggle,
            colors = androidx.compose.material3.SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = JourneyGreen,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFF3A3020)
            )
        )

        // ── الاسم والوقت ─────────────────────────
        Column(horizontalAlignment = Alignment.End) {
            Text(text = name, color = WhiteColor, fontFamily = AlmaraiFont, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = time, color = SubtitleColor, fontFamily = IbmPlexArabicFont, fontSize = 13.sp)
        }

        // ── الأيقونة ─────────────────────────────
        Image(
            painter = painterResource(id = icon),
            contentDescription = name,
            modifier = Modifier.size(24.dp)
        )

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrayerNotificationScreenPreview() {
    PrayerNotificationScreen()
}