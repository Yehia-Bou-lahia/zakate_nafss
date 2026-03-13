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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ramadan.R
import com.example.ramadan.data.DashboardViewModel
import com.example.ramadan.ui.components.BottomNavBar
import com.example.ramadan.ui.theme.*
import kotlinx.coroutines.launch
import java.util.Calendar

// ── ورد اليوم — يتجدد يومياً ─────────────────────────
private val ayahList = listOf(
    Triple("شَهْرُ رَمَضَانَ الَّذِي أُنزِلَ فِيهِ الْقُرْآنُ هُدًى لِّلنَّاسِ", "البقرة", "185"),
    Triple("وَإِذَا سَأَلَكَ عِبَادِي عَنِّي فَإِنِّي قَرِيبٌ أُجِيبُ دَعْوَةَ الدَّاعِ إِذَا دَعَانِ", "البقرة", "186"),
    Triple("وَمَن يَتَّقِ اللَّهَ يَجْعَل لَّهُ مَخْرَجًا وَيَرْزُقْهُ مِنْ حَيْثُ لَا يَحْتَسِبُ", "الطلاق", "2"),
    Triple("فَإِنَّ مَعَ الْعُسْرِ يُسْرًا إِنَّ مَعَ الْعُسْرِ يُسْرًا", "الشرح", "5-6"),
    Triple("إِنَّ اللَّهَ مَعَ الصَّابِرِينَ", "البقرة", "153"),
    Triple("وَاسْتَعِينُوا بِالصَّبْرِ وَالصَّلَاةِ وَإِنَّهَا لَكَبِيرَةٌ إِلَّا عَلَى الْخَاشِعِينَ", "البقرة", "45"),
    Triple("إِنَّ الَّذِينَ آمَنُوا وَعَمِلُوا الصَّالِحَاتِ كَانَتْ لَهُمْ جَنَّاتُ الْفِرْدَوْسِ نُزُلًا", "الكهف", "107")
)

private val duaaList = listOf(
    "اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي",
    "اللَّهُمَّ أَعِنِّي عَلَى ذِكْرِكَ وَشُكْرِكَ وَحُسْنِ عِبَادَتِكَ",
    "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ",
    "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْهُدَى وَالتُّقَى وَالْعَفَافَ وَالْغِنَى",
    "رَبِّ اشْرَحْ لِي صَدْرِي وَيَسِّرْ لِي أَمْرِي",
    "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْهَمِّ وَالْحَزَنِ وَالْعَجْزِ وَالْكَسَلِ",
    "اللَّهُمَّ اجْعَلْنَا مِنَ الَّذِينَ يَسْتَمِعُونَ الْقَوْلَ فَيَتَّبِعُونَ أَحْسَنَهُ"
)

private val hadithList = listOf(
    Pair("مَنْ صَامَ رَمَضَانَ إِيمَاناً وَاحْتِسَاباً غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ", "متفق عليه"),
    Pair("إِذَا جَاءَ رَمَضَانُ فُتِّحَتْ أَبْوَابُ الْجَنَّةِ وَغُلِّقَتْ أَبْوَابُ النَّارِ", "متفق عليه"),
    Pair("الصَّوْمُ جُنَّةٌ فَلَا يَرْفُثْ وَلَا يَجْهَلْ", "متفق عليه"),
    Pair("لِلصَّائِمِ فَرْحَتَانِ فَرْحَةٌ عِنْدَ فِطْرِهِ وَفَرْحَةٌ عِنْدَ لِقَاءِ رَبِّهِ", "متفق عليه"),
    Pair("مَنْ قَامَ رَمَضَانَ إِيمَاناً وَاحْتِسَاباً غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ", "متفق عليه"),
    Pair("خَيْرُكُمْ مَنْ تَعَلَّمَ الْقُرْآنَ وَعَلَّمَهُ", "البخاري"),
    Pair("الْمُؤْمِنُ الَّذِي يَقْرَأُ الْقُرْآنَ كَالْأُتْرُجَّةِ طَعْمُهَا طَيِّبٌ وَرِيحُهَا طَيِّبٌ", "متفق عليه")
)


@Composable
fun DhikrScreen(
    onNavigate: (String) -> Unit = {},
    dashboardViewModel: DashboardViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    var tasbihCount by remember { mutableStateOf(0) }
    var tasbihGoal  by remember { mutableStateOf(100) }
    val progress = (tasbihCount.toFloat() / tasbihGoal.toFloat()).coerceIn(0f, 1f)
    val (hijriDay, hijriMonth, hijriYear) = remember { getHijriDate() }

    val totalPrayers by dashboardViewModel.totalPrayers.collectAsState()
    val totalSadaqah by dashboardViewModel.totalSadaqah.collectAsState()
    val completedJuz by dashboardViewModel.completedJuzCount.collectAsState()

    var showKhatmaDialog by remember { mutableStateOf(false) }
    val currentKhatma = dashboardViewModel.getCurrentKhatma(completedJuz ?: 0)
    val currentJuz    = dashboardViewModel.getCurrentJuz(1, completedJuz ?: 0)

    val hour      = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
    val greeting  = when { hour < 12 -> "صباح الخير"; hour < 17 -> "مساء الخير"; else -> "السلام عليكم" }

    val currentAyah   = ayahList[dayOfYear % ayahList.size]
    val currentDuaa   = duaaList[dayOfYear % duaaList.size]
    val currentHadith = hadithList[dayOfYear % hadithList.size]

    if (showKhatmaDialog) {
        AlertDialog(
            onDismissRequest = { showKhatmaDialog = false },
            containerColor = Color(0xFF0D1F0D),
            title = { Text(text = "🎉 ما شاء الله!", fontFamily = AlmaraiFont, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = GoldColor, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
            text = { Text(text = "أتممت ختمة القرآن الكريم كاملةً!\nبارك الله فيك وجعله في ميزان حسناتك.\nهل تبدأ ختمة جديدة؟", fontFamily = IbmPlexArabicFont, fontSize = 14.sp, color = WhiteColor, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
            confirmButton = {
                TextButton(onClick = { showKhatmaDialog = false; scope.launch { dashboardViewModel.resetQuranProgress(1); dashboardViewModel.unlockAchievement("📖", "أتممت ختمة القرآن الكريم") } }) {
                    Text(text = "ابدأ ختمة جديدة", color = GreenLight, fontFamily = AlmaraiFont, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = { TextButton(onClick = { showKhatmaDialog = false }) { Text(text = "لاحقاً", color = SubtitleColor, fontFamily = IbmPlexArabicFont) } }
        )
    }

    Scaffold(
        bottomBar = { BottomNavBar(selectedRoute = "dhikr", onItemSelected = { onNavigate(it) }) },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().background(brush = Brush.verticalGradient(colorStops = arrayOf(0.0f to BgTop, 0.5f to BgMid, 1.0f to BgBottom)))) {
            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(innerPadding).padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(52.dp))

                // ══ TopBar ═══════════════════════════
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(WhiteColor.copy(alpha = 0.08f)).border(1.dp, WhiteColor.copy(alpha = 0.12f), RoundedCornerShape(12.dp))) {
                        Column(verticalArrangement = Arrangement.spacedBy(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            repeat(3) { Box(modifier = Modifier.width(18.dp).height(2.dp).clip(RoundedCornerShape(50)).background(WhiteColor)) }
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = greeting, fontFamily = AlmaraiFont, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = WhiteColor)
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(painter = painterResource(id = R.drawable.calendar), contentDescription = null, tint = SubtitleColor, modifier = Modifier.size(12.dp))
                            Text(text = "$hijriDay $hijriMonth $hijriYear هـ", fontFamily = IbmPlexArabicFont, fontSize = 12.sp, color = SubtitleColor)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ══ عداد التسبيح ════════════════════
                Text(text = "عداد التسبيح", fontFamily = AlmaraiFont, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp, color = WhiteColor)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "%${(progress * 100).toInt()}", color = GreenLight, fontFamily = IbmPlexArabicFont, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(SubtitleColor))
                    Text(text = "الهدف: $tasbihGoal", color = SubtitleColor, fontFamily = IbmPlexArabicFont, fontSize = 13.sp)
                }
                Spacer(modifier = Modifier.height(24.dp))
                TasbihCircle(count = tasbihCount, progress = progress, onClick = { tasbihCount++ })
                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    listOf("الله أكبر", "الحمد لله", "سبحان الله").forEach { dhikr ->
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f).clip(RoundedCornerShape(12.dp)).background(WhiteColor.copy(alpha = 0.06f)).border(1.dp, GreenLight.copy(alpha = 0.3f), RoundedCornerShape(12.dp)).clickable { tasbihCount++ }.padding(vertical = 12.dp)) {
                            Text(text = dhikr, color = WhiteColor, fontFamily = IbmPlexArabicFont, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    TasbihControlButton(icon = R.drawable.loop, label = "إعادة", onClick = { tasbihCount = 0 })
                    TasbihControlButton(icon = R.drawable.check, label = "حفظ", isActive = true, onClick = { scope.launch { dashboardViewModel.unlockAchievement("📿", "سبّح $tasbihCount مرة") } })
                    TasbihControlButton(icon = R.drawable.goal, label = "هدف", onClick = { tasbihGoal = when (tasbihGoal) { 100 -> 33; 33 -> 99; else -> 100 } })
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ══ الأهداف اليومية ══════════════════
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(4.dp))
                    Text(text = "الأهداف اليومية", fontFamily = AlmaraiFont, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = WhiteColor)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DailyGoalCard2x2(modifier = Modifier.weight(1f), icon = R.drawable.mosque, title = "الصلوات الخمس", value = "${(totalPrayers ?: 0).coerceAtMost(5)} من ٥", progress = ((totalPrayers ?: 0) / 5f).coerceIn(0f, 1f), progressColor = Color(0xFF4A90D9), isCompleted = (totalPrayers ?: 0) >= 5, onClick = { if ((totalPrayers ?: 0) < 5) scope.launch { dashboardViewModel.addTodayProgress(prayers = 1) } })
                    DailyGoalCard2x2(modifier = Modifier.weight(1f), icon = R.drawable.asar, title = "القرآن الكريم", value = "جزء $currentJuz من ٣٠", progress = ((completedJuz ?: 0) / 30f).coerceIn(0f, 1f), progressColor = GoldColor, isCompleted = (completedJuz ?: 0) >= 30, onClick = { if ((completedJuz ?: 0) < 30) { scope.launch { dashboardViewModel.completeJuz(currentJuz, currentKhatma); if (currentJuz == 30) showKhatmaDialog = true } } else showKhatmaDialog = true })
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DailyGoalCard2x2(modifier = Modifier.weight(1f), icon = R.drawable.star, title = "الصدقة اليومية", value = if ((totalSadaqah ?: 0) > 0) "تم الإنجاز ✓" else "اضغط للتسجيل", progress = if ((totalSadaqah ?: 0) > 0) 1f else 0f, progressColor = GreenLight, isCompleted = (totalSadaqah ?: 0) > 0, onClick = { scope.launch { dashboardViewModel.addTodayProgress(sadaqah = 1) } })
                    DailyGoalCard2x2(modifier = Modifier.weight(1f), icon = R.drawable.hillal, title = "صيام رمضان", value = if (hour in 4..18) "جارٍ الصيام" else "منتهٍ للإفطار", progress = if (hour in 4..18) ((hour - 4) / 14f).coerceIn(0f, 1f) else 1f, progressColor = Color(0xFF9B59B6), isCompleted = hour > 18, onClick = { scope.launch { dashboardViewModel.addTodayProgress(fastingHours = 1); dashboardViewModel.unlockAchievement("🌙", "أتممت صيام اليوم") } })
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ══ الأذكار ══════════════════════════
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(text = "الأذكار", fontFamily = AlmaraiFont, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = WhiteColor)
                }
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AdhkarNavigationCard(modifier = Modifier.weight(1f), title = "أذكار الصباح", emoji = "☀️", accentColor = GoldColor, total = adhkarSabahList.size, onClick = { onNavigate("adhkar/sabah") })
                    AdhkarNavigationCard(modifier = Modifier.weight(1f), title = "أذكار المساء", emoji = "🌙", accentColor = Color(0xFF9B59B6), total = adhkarMasaaList.size, onClick = { onNavigate("adhkar/masaa") })
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ══ ورد اليوم ════════════════════════
                WardAlYawmCard(ayah = currentAyah, duaa = currentDuaa, hadith = currentHadith)

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}


// ── بطاقة تنقل للأذكار ───────────────────────────────
@Composable
fun AdhkarNavigationCard(modifier: Modifier = Modifier, title: String, emoji: String, accentColor: Color, total: Int, onClick: () -> Unit) {
    Box(modifier = modifier.clip(RoundedCornerShape(16.dp)).background(accentColor.copy(alpha = 0.08f)).border(1.dp, accentColor.copy(alpha = 0.3f), RoundedCornerShape(16.dp)).clickable { onClick() }.padding(16.dp)) {
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
            Text(text = emoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, fontFamily = AlmaraiFont, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = WhiteColor, textAlign = TextAlign.End)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$total ذكر", fontFamily = IbmPlexArabicFont, fontSize = 11.sp, color = SubtitleColor, textAlign = TextAlign.End)
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "ابدأ", fontFamily = IbmPlexArabicFont, fontSize = 11.sp, color = accentColor, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(4.dp))
                Box(modifier = Modifier.size(20.dp).clip(CircleShape).background(accentColor.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                    Text(text = "←", color = accentColor, fontSize = 11.sp)
                }
            }
        }
    }
}


// ══ بطاقة ورد اليوم ══════════════════════════════════
@Composable
fun WardAlYawmCard(ayah: Triple<String, String, String>, duaa: String, hadith: Pair<String, String>) {
    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(WhiteColor.copy(alpha = 0.06f)).border(1.dp, GoldColor.copy(alpha = 0.2f), RoundedCornerShape(20.dp)).padding(16.dp)) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(GoldColor.copy(alpha = 0.15f)).padding(horizontal = 10.dp, vertical = 4.dp)) {
                    Text(text = "يتجدد يومياً", color = GoldColor, fontFamily = IbmPlexArabicFont, fontSize = 11.sp)
                }
                Text(text = "📖 ورد اليوم", fontFamily = AlmaraiFont, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = WhiteColor)
            }
            Spacer(modifier = Modifier.height(16.dp))

            SectionDivider(label = "آية اليوم", color = GoldColor)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "﴾ ${ayah.first} ﴿", fontFamily = AmiriFont, fontSize = 16.sp, color = WhiteColor, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth(), lineHeight = 26.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "سورة ${ayah.second} — الآية ${ayah.third}", color = SubtitleColor, fontFamily = IbmPlexArabicFont, fontSize = 11.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)

            Spacer(modifier = Modifier.height(16.dp))
            SectionDivider(label = "دعاء اليوم", color = GreenLight)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = duaa, fontFamily = AmiriFont, fontSize = 15.sp, color = WhiteColor, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth(), lineHeight = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))
            SectionDivider(label = "حديث اليوم", color = Color(0xFF4A90D9))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "« ${hadith.first} »", fontFamily = AmiriFont, fontSize = 15.sp, color = WhiteColor, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth(), lineHeight = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "رواه ${hadith.second}", color = SubtitleColor, fontFamily = IbmPlexArabicFont, fontSize = 11.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
        }
    }
}


// ── فاصل القسم ───────────────────────────────────────
@Composable
fun SectionDivider(label: String, color: Color) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
        Box(modifier = Modifier.weight(1f).height(1.dp).background(WhiteColor.copy(alpha = 0.08f)))
        Spacer(modifier = Modifier.width(8.dp))
        Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(color.copy(alpha = 0.15f)).padding(horizontal = 8.dp, vertical = 3.dp)) {
            Text(text = label, color = color, fontFamily = IbmPlexArabicFont, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}


// ── بطاقة هدف 2×2 ────────────────────────────────────
@Composable
fun DailyGoalCard2x2(modifier: Modifier = Modifier, icon: Int, title: String, value: String, progress: Float, progressColor: Color, isCompleted: Boolean, onClick: () -> Unit = {}) {
    Box(modifier = modifier.clip(RoundedCornerShape(16.dp)).background(if (isCompleted) progressColor.copy(alpha = 0.12f) else WhiteColor.copy(alpha = 0.05f)).border(1.dp, if (isCompleted) progressColor.copy(alpha = 0.4f) else WhiteColor.copy(alpha = 0.08f), RoundedCornerShape(16.dp)).clickable { onClick() }.padding(14.dp)) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                if (isCompleted) Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(progressColor.copy(alpha = 0.2f)).padding(horizontal = 6.dp, vertical = 3.dp)) { Text(text = "✓ تم", color = progressColor, fontFamily = IbmPlexArabicFont, fontSize = 10.sp, fontWeight = FontWeight.Bold) } else Box(modifier = Modifier.size(8.dp))
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(progressColor.copy(alpha = 0.15f))) { Icon(painter = painterResource(id = icon), contentDescription = null, tint = progressColor, modifier = Modifier.size(20.dp)) }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = value, fontFamily = AlmaraiFont, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = WhiteColor, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = title, color = SubtitleColor, fontFamily = IbmPlexArabicFont, fontSize = 11.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(50)).background(WhiteColor.copy(alpha = 0.08f))) {
                Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(progress).clip(RoundedCornerShape(50)).background(progressColor))
            }
        }
    }
}


// ── دائرة عداد التسبيح ───────────────────────────────
@Composable
fun TasbihCircle(count: Int, progress: Float, onClick: () -> Unit) {
    val animatedProgress by animateFloatAsState(targetValue = progress, animationSpec = tween(300), label = "tasbih")
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(220.dp).clickable { onClick() }) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 12.dp.toPx()
            drawArc(color = Color(0xFF1A3A1A), startAngle = -90f, sweepAngle = 360f, useCenter = false, style = Stroke(width = strokeWidth))
            drawArc(brush = Brush.sweepGradient(listOf(GreenDark, GreenLight, GreenLight)), startAngle = -90f, sweepAngle = 360f * animatedProgress, useCenter = false, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(190.dp).clip(CircleShape).background(Color(0xFF0D1F0D))) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = count.toString(), fontFamily = AlmaraiFont, fontWeight = FontWeight.ExtraBold, fontSize = 64.sp, color = WhiteColor)
                Text(text = "العدد", fontFamily = IbmPlexArabicFont, fontSize = 14.sp, color = SubtitleColor)
            }
        }
    }
}


// ── زر تحكم التسبيح ──────────────────────────────────
@Composable
fun TasbihControlButton(icon: Int, label: String, isActive: Boolean = false, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(52.dp).clip(CircleShape).background(if (isActive) GreenLight.copy(alpha = 0.2f) else WhiteColor.copy(alpha = 0.06f)).border(1.dp, if (isActive) GreenLight.copy(alpha = 0.5f) else WhiteColor.copy(alpha = 0.1f), CircleShape)) {
            Icon(painter = painterResource(id = icon), contentDescription = label, tint = if (isActive) GreenLight else SubtitleColor, modifier = Modifier.size(22.dp))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = label, color = if (isActive) GreenLight else SubtitleColor, fontFamily = IbmPlexArabicFont, fontSize = 11.sp, textAlign = TextAlign.Center)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DhikrScreenPreview() {
    DhikrScreen()
}