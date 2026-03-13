package com.example.ramadan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ramadan.ui.theme.*

// ── بيانات الأذكار ────────────────────────────────────
val adhkarSabahList = listOf(
    Triple("أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ وَالْحَمْدُ لِلَّهِ لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ", "مرة واحدة", "أبو داود"),
    Triple("اللَّهُمَّ بِكَ أَصْبَحْنَا وَبِكَ أَمْسَيْنَا وَبِكَ نَحْيَا وَبِكَ نَمُوتُ وَإِلَيْكَ النُّشُورُ", "مرة واحدة", "الترمذي"),
    Triple("اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ خَلَقْتَنِي وَأَنَا عَبْدُكَ وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ", "مرة واحدة", "البخاري"),
    Triple("اللَّهُمَّ عَافِنِي فِي بَدَنِي اللَّهُمَّ عَافِنِي فِي سَمْعِي اللَّهُمَّ عَافِنِي فِي بَصَرِي لَا إِلَهَ إِلَّا أَنْتَ", "3 مرات", "أبو داود"),
    Triple("أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ مِنْ شَرِّ مَا خَلَقَ", "3 مرات", "مسلم"),
    Triple("بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الْأَرْضِ وَلَا فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ", "3 مرات", "أبو داود"),
    Triple("رَضِيتُ بِاللَّهِ رَبًّا وَبِالْإِسْلَامِ دِينًا وَبِمُحَمَّدٍ ﷺ نَبِيًّا", "3 مرات", "أبو داود"),
    Triple("سُبْحَانَ اللَّهِ وَبِحَمْدِهِ", "100 مرة", "مسلم")
)

val adhkarMasaaList = listOf(
    Triple("أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ وَالْحَمْدُ لِلَّهِ لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ", "مرة واحدة", "أبو داود"),
    Triple("اللَّهُمَّ بِكَ أَمْسَيْنَا وَبِكَ أَصْبَحْنَا وَبِكَ نَحْيَا وَبِكَ نَمُوتُ وَإِلَيْكَ الْمَصِيرُ", "مرة واحدة", "الترمذي"),
    Triple("اللَّهُمَّ إِنِّي أَمْسَيْتُ أُشْهِدُكَ وَأُشْهِدُ حَمَلَةَ عَرْشِكَ وَمَلَائِكَتَكَ وَجَمِيعَ خَلْقِكَ أَنَّكَ أَنْتَ اللَّهُ لَا إِلَهَ إِلَّا أَنْتَ وَحْدَكَ لَا شَرِيكَ لَكَ", "4 مرات", "أبو داود"),
    Triple("اللَّهُمَّ عَافِنِي فِي بَدَنِي اللَّهُمَّ عَافِنِي فِي سَمْعِي اللَّهُمَّ عَافِنِي فِي بَصَرِي لَا إِلَهَ إِلَّا أَنْتَ", "3 مرات", "أبو داود"),
    Triple("اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَفْوَ وَالْعَافِيَةَ فِي الدُّنْيَا وَالْآخِرَةِ", "مرة واحدة", "ابن ماجه"),
    Triple("أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ مِنْ شَرِّ مَا خَلَقَ", "3 مرات", "مسلم"),
    Triple("اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْهَمِّ وَالْحَزَنِ وَالْعَجْزِ وَالْكَسَلِ", "مرة واحدة", "البخاري"),
    Triple("سُبْحَانَ اللَّهِ وَبِحَمْدِهِ", "100 مرة", "مسلم")
)


@Composable
fun AdhkarScreen(
    type: String = "sabah",
    onBack: () -> Unit = {}
) {
    val isSabah   = type == "sabah"
    val adhkar    = if (isSabah) adhkarSabahList else adhkarMasaaList
    val title     = if (isSabah) "أذكار الصباح" else "أذكار المساء"
    val accentColor = if (isSabah) GoldColor else Color(0xFF9B59B6)

    val completed = remember { mutableStateListOf<Int>() }
    val allDone   = completed.size == adhkar.size

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
                // ── زر الرجوع ──────────────────────
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(WhiteColor.copy(alpha = 0.08f))
                        .border(1.dp, WhiteColor.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
                        .clickable { onBack() }
                ) {
                    Text(text = "→", color = WhiteColor, fontSize = 18.sp)
                }

                Text(
                    text = title,
                    fontFamily = AlmaraiFont,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = accentColor
                )

                // ── مؤشر التقدم ────────────────────
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(accentColor.copy(alpha = 0.15f))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "${completed.size}/${adhkar.size}",
                        color = accentColor,
                        fontFamily = IbmPlexArabicFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── شريط التقدم الكلي ─────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(50))
                    .background(WhiteColor.copy(alpha = 0.08f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth((completed.size.toFloat() / adhkar.size.toFloat()).coerceIn(0f, 1f))
                        .clip(RoundedCornerShape(50))
                        .background(accentColor)
                )
            }

            if (allDone) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(accentColor.copy(alpha = 0.15f))
                        .border(1.dp, accentColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✨ أتممت $title — بارك الله فيك",
                        color = accentColor,
                        fontFamily = AlmaraiFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ══ قائمة الأذكار ═══════════════════════
            adhkar.forEachIndexed { idx, (dhikr, count, source) ->
                val isDone = completed.contains(idx)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (isDone) accentColor.copy(alpha = 0.1f)
                            else WhiteColor.copy(alpha = 0.05f)
                        )
                        .border(
                            1.dp,
                            if (isDone) accentColor.copy(alpha = 0.35f)
                            else WhiteColor.copy(alpha = 0.08f),
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { if (!isDone) completed.add(idx) }
                        .padding(16.dp)
                ) {
                    Column {
                        // ── رقم + العداد + المصدر ───
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // ── Checkbox ──────────────
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (isDone) accentColor.copy(alpha = 0.2f)
                                        else WhiteColor.copy(alpha = 0.06f)
                                    )
                                    .border(
                                        1.5.dp,
                                        if (isDone) accentColor else WhiteColor.copy(alpha = 0.2f),
                                        RoundedCornerShape(8.dp)
                                    )
                            ) {
                                if (isDone) Text(text = "✓", color = accentColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }

                            // ── العداد + المصدر ───────
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(WhiteColor.copy(alpha = 0.06f))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(text = source, color = SubtitleColor, fontFamily = IbmPlexArabicFont, fontSize = 10.sp)
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(accentColor.copy(alpha = 0.15f))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(text = count, color = accentColor, fontFamily = IbmPlexArabicFont, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // ── نص الذكر ─────────────────
                        Text(
                            text = dhikr,
                            fontFamily = AmiriFont,
                            fontSize = 17.sp,
                            color = if (isDone) SubtitleColor else WhiteColor,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth(),
                            lineHeight = 28.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AdhkarScreenPreview() {
    AdhkarScreen(type = "sabah")
}