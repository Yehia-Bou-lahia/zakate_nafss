# 🌙 Ramadan Tracker — تطبيق رمضان

<div align="center">

![Platform](https://img.shields.io/badge/Platform-Android-green?style=flat-square&logo=android)
![Language](https://img.shields.io/badge/Language-Kotlin-purple?style=flat-square&logo=kotlin)
![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-blue?style=flat-square&logo=jetpackcompose)
![Database](https://img.shields.io/badge/Database-Room-orange?style=flat-square)
![Branch](https://img.shields.io/badge/Branch-feat%2Fwelcome--screen-brightgreen?style=flat-square&logo=git)

**تطبيق أندرويد متكامل لمتابعة العبادات والأهداف الرمضانية**

</div>

---

## 📋 نظرة عامة

تطبيق **رمضان تراكر** هو مرافق روحاني شامل يساعد المسلم على متابعة عباداته اليومية خلال شهر رمضان المبارك. يشمل التطبيق عداد التسبيح، متابعة ختمة القرآن، الأذكار اليومية، الصلوات الخمس، الصيام، والصدقة — كل ذلك في واجهة عربية أنيقة بتصميم داكن.

---

## ✨ المميزات الرئيسية

- 🕌 **متابعة الصلوات الخمس** يومياً مع تسجيل تلقائي
- 📖 **ختمة القرآن** مع دعم 1-3 ختمات وتتبع كل جزء
- 📿 **عداد التسبيح** مع دائرة تقدم متحركة وأزرار أذكار سريعة
- 🌅 **أذكار الصباح والمساء** مع تتبع الإكمال
- 📅 **ورد اليوم** — آية وحديث ودعاء يتجددان يومياً تلقائياً
- 💰 **تسجيل الصدقة** بضغطة واحدة
- 🌙 **متابعة الصيام** مع عداد وقت تلقائي
- 🏆 **نظام الإنجازات** يفتح تلقائياً عند إتمام الأهداف
- 📊 **إحصائيات شاملة** مع رسم بياني للنمو الروحاني
- 🗓️ **التقويم الهجري** الدقيق (UmmalquraCalendar)

---

## 📱 الشاشات

### 1. 🎉 شاشة الترحيب — WelcomeScreen

شاشة بداية التطبيق مع تصميم فاخر.

**المحتوى:**
- فانوس رمضاني مع تأثيرات توهج ذهبية
- عنوان "رمضان كريم" بخط Almarai العريض
- زر "ابدأ رحلتك الرمضانية" بتصميم ذهبي
- خلفية gradient داكنة من أخضر عميق إلى أزرق ليلي

---

### 2. 🧭 شاشة الإعداد — OnboardingScreen

تخصيص التجربة حسب اهتمام المستخدم.

**المحتوى:**
- 4 بطاقات عبادة قابلة للتحديد: الصلاة / القرآن / الذكر / الصدقة
- slider لتحديد شدة الالتزام
- حالة تحديد مرئية مع توهج أخضر
- زر المتابعة ينشط بعد اختيار هدف واحد على الأقل

---

### 3. 👤 إعداد الملف الشخصي — ProfileSetupScreen

جمع بيانات المستخدم لتخصيص التجربة.

**المحتوى:**
- رفع صورة شخصية من المعرض
- حقل الاسم الكامل
- تحديد الموقع الجغرافي عبر GPS (لحساب أوقات الصلاة)
- Dropdown اختيار المذهب الفقهي (حنبلي / شافعي / مالكي / حنفي)
- شريط تقدم Onboarding (66%)
- زر "حفظ ومتابعة"

---

### 4. 🏠 الشاشة الرئيسية — DhikrScreen

الشاشة المركزية للتطبيق.

#### قسم عداد التسبيح
- دائرة تفاعلية كبيرة (220dp) — اضغط عليها للعد
- قوس تقدم متحرك (Animated Arc) بلون أخضر
- نسبة مئوية تتحدث لحظياً
- 3 أزرار أذكار سريعة: **سبحان الله / الحمد لله / الله أكبر**
- أزرار تحكم:
  - **إعادة** — يصفر العداد
  - **حفظ** — يسجل إنجاز "سبّح X مرة" في قاعدة البيانات
  - **هدف** — يدور بين 33 / 99 / 100

#### قسم الأهداف اليومية (شبكة 2×2)

| البطاقة | الوظيفة |
|---------|---------|
| 🕌 الصلوات الخمس | كل ضغطة = صلاة واحدة، تتوقف عند 5، تعود يومياً |
| 📖 القرآن الكريم | كل ضغطة = جزء كامل، dialog عند إكمال الختمة |
| 💰 الصدقة اليومية | ضغطة واحدة لتسجيل الصدقة |
| 🌙 صيام رمضان | عداد تلقائي حسب الوقت + إنجاز عند الإكمال |

كل بطاقة تعرض:
- شريط تقدم ملون
- badge "✓ تم" عند الإكمال
- تغيير لون الحدود والخلفية عند الإكمال

#### قسم الأذكار
- بطاقتان: **أذكار الصباح ☀️** و **أذكار المساء 🌙**
- الضغط ينقل لصفحة الأذكار الكاملة
- عدد الأذكار المتاحة في كل بطاقة

#### قسم ورد اليوم
- **آية اليوم** — تتغير يومياً بخط Amiri مع اسم السورة والآية
- **دعاء اليوم** — من الأدعية المأثورة يتجدد يومياً
- **حديث اليوم** — حديث نبوي مع مصدره يتجدد يومياً

---

### 5. 📿 صفحة الأذكار — AdhkarScreen

صفحة مستقلة لقراءة الأذكار كاملة.

**المحتوى:**
- TopBar مع زر رجوع وعداد `X/Y`
- شريط تقدم علوي
- قائمة الأذكار كاملة مع:
  - checkbox دائري لكل ذكر
  - نص الذكر بخط Amiri بحجم مريح للقراءة
  - ترقيم كل ذكر
  - تغيير لون العنصر عند التحديد
- رسالة تشجيعية عند إكمال الأذكار كلها
- زر "إعادة القراءة"
- فتح إنجاز تلقائي عند الإكمال

**يدعم:**
- `AdhkarScreen(type = "sabah")` — أذكار الصباح (8 أذكار)
- `AdhkarScreen(type = "masaa")` — أذكار المساء (8 أذكار)

---

### 6. 📖 صفحة القرآن — QuranScreen

متابعة ختمة القرآن الكريم.

**المحتوى:**

#### اختيار الختمة
- Toggle بين: **ختمة واحدة / ختمتان / ٣ ختمات**
- تهيئة قاعدة البيانات تلقائياً عند الاختيار

#### الفانوس
- صورة فانوس رمضاني حقيقية
- يمتلئ من **الداخل** باللون الأخضر (Canvas + clipPath بيضاوي)
- Animation سلسة عند تغير التقدم
- نسبة مئوية مركزية تتحدث مع كل جزء

#### بطاقة الهدف اليومي
- عدد الصفحات اليومية المطلوبة
- نطاق الصفحات الحالي مع اسم السورة
- نسبة مئوية حقيقية من قاعدة البيانات
- زر **"سجل القراءة"** — يكمل الجزء الحالي تلقائياً
- أيقونات حقيقية (calendar, pen, checked)

#### قائمة الأجزاء
- 30 × عدد الختمات جزء
- كل جزء يعرض:
  - Checkbox قابل للضغط
  - رقم الجزء بالأرقام العربية
  - اسم سورة البداية والنهاية
  - أرقام الآيات
  - أول كلمات الجزء
  - badge "الحالي" (أخضر) أو "تم" (أخضر داكن)
- حدود خضراء للجزء الحالي

#### Dialog إتمام الختمة
- يظهر عند الوصول للجزء 30
- رسالة تشجيعية "🎉 أتممت الختمة!"
- زر "تأكيد" — يصفر التقدم ويبدأ ختمة جديدة
- فتح إنجاز "أتممت ختمة القرآن الكريم"

---

### 7. 📊 صفحة الإحصائيات — DashboardScreen

عرض شامل لتقدم المستخدم.

**المحتوى:**

#### TopBar
- أيقونة الملف الشخصي
- زر الإعدادات

#### العنوان
- "رحلتي الرمضانية"
- "اليوم X من 30" بالتاريخ الهجري الدقيق

#### بطاقة التاريخ الهجري
- صورة هلال رمضان
- اليوم والشهر الهجري
- السنة الهجرية
- اسم اليوم بالعربية

#### شبكة الإحصائيات (2×2)

| البطاقة | المصدر | اللون |
|---------|--------|-------|
| صلوات في وقتها | `totalPrayers` | أزرق |
| أجزاء مكتملة | `totalQuranPages` | أخضر |
| التتابع الحالي | `streakDays` | بنفسجي |
| صدقات ممنوحة | `totalSadaqah` | برتقالي |

كل بطاقة: قيمة + label + شريط تقدم ملون

#### بطاقة النمو الروحاني
- رسم بياني Canvas لآخر 7 أيام
- منحنى ناعم بلون ذهبي
- تظليل gradient تحت المنحنى
- نقاط دائرية على كل يوم
- أسماء الأيام بالعربية

#### بطاقة الإنجازات
- قائمة بآخر الإنجازات المفتوحة
- كل إنجاز: emoji + عنوان + تاريخ
- رسالة تشجيعية عند عدم وجود إنجازات

---

## 🗄️ قاعدة البيانات

### Room Database — الإصدار 3

#### UserProgress
```kotlin
@Entity(tableName = "user_progress")
data class UserProgress(
    val date: String,           // yyyy-MM-dd
    val prayersOnTime: Int,     // عدد الصلوات
    val quranPages: Int,        // صفحات القرآن
    val fastingHours: Int,      // ساعات الصيام
    val sadaqah: Int            // مبلغ الصدقة
)
```

#### Achievement
```kotlin
@Entity(tableName = "achievements")
data class Achievement(
    val emoji: String,          // رمز الإنجاز
    val title: String,          // عنوان الإنجاز
    val date: String            // تاريخ الفتح
)
```

#### QuranProgress
```kotlin
@Entity(tableName = "quran_progress")
data class QuranProgress(
    val juzNumber: Int,         // رقم الجزء (1-30)
    val khatmaNumber: Int,      // رقم الختمة
    val isCompleted: Boolean,   // هل تم؟
    val completedDate: String?  // تاريخ الإكمال
)
```

### DashboardViewModel — العمليات

| الدالة | الوصف |
|--------|-------|
| `addTodayProgress()` | تضيف تقدم اليوم (صلاة / قرآن / صيام / صدقة) |
| `unlockAchievement()` | تفتح إنجازاً جديداً |
| `initQuranProgress()` | تهيئ جدول القرآن (IGNORE if exists) |
| `completeJuz()` | تعلم جزءاً كمكتمل |
| `resetQuranProgress()` | تصفر التقدم وتبدأ ختمة جديدة |
| `getLanternProgress()` | تحسب نسبة امتلاء الفانوس (0f-1f) |

---

## 🧭 بنية التنقل

```
WelcomeScreen
      ↓
OnboardingScreen  ←→ (back)
      ↓
ProfileSetupScreen
      ↓
QuranScreen (startDestination بعد Onboarding)
      │
      ├── [BottomNavBar - RTL]
      │     ├── 🏠 الرئيسية  → quran
      │     ├── 📖 القرآن   → quran  
      │     ├── ➕ إضافة    → add
      │     ├── 📊 إحصائيات → dashboard
      │     └── 📿 الذكر    → dhikr
      │
      ├── DashboardScreen
      ├── DhikrScreen
      │     └── adhkar/{type}
      │           ├── AdhkarScreen(sabah)
      │           └── AdhkarScreen(masaa)
      └── QuranScreen
```

### NavGraph — popUpTo Strategy
```kotlin
navController.navigate(route) {
    popUpTo(Routes.QURAN) { saveState = true }
    launchSingleTop = true
    restoreState = true
}
```
يمنع تراكم الصفحات في الـ backstack عند التنقل بين صفحات الـ BottomNavBar.

---

## 🎨 نظام التصميم

### الألوان
```kotlin
val BgTop     = Color(0xFF0B1026)   // خلفية أعلى
val BgMid     = Color(0xFF0D1F14)   // خلفية وسط
val BgBottom  = Color(0xFF0A1F1D)   // خلفية أسفل
val GoldColor = Color(0xFFE8B84B)   // ذهبي رئيسي
val GoldDark  = Color(0xFFC9A84C)   // ذهبي داكن
val GreenLight = Color(0xFF2ECC71)  // أخضر فاتح
val GreenDark  = Color(0xFF1A8A4A)  // أخضر داكن
val CardBg    = Color(0xFF0D1F0D)   // خلفية البطاقات
val WhiteColor = Color(0xFFFFFFFF)  // أبيض
val SubtitleColor = Color(0xFF7A8FA6) // رمادي للنصوص الثانوية
```

### الخطوط
```kotlin
val AlmaraiFont        // العناوين الرئيسية
val IbmPlexArabicFont  // النصوص العادية والـ UI
val AmiriFont          // نصوص القرآن والأذكار
```

### مبادئ التصميم
- **RTL كامل** — كل واجهة من اليمين لليسار
- **Dark Mode فقط** — لا يوجد Light Mode
- **Glassmorphism** — بطاقات شفافة مع حدود خفيفة
- **Gradient Backgrounds** — خلفيات متدرجة على كل شاشة
- **Animated Components** — تأثيرات حركية على الفانوس، الدوائر، وأشرطة التقدم

---

## 📦 المكتبات

```kotlin
// Jetpack Compose
androidx.compose.bom = "2024.x"
androidx.compose.material3

// Navigation
androidx.navigation:navigation-compose

// ViewModel
androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7

// Room Database
androidx.room:room-runtime:2.6.1
androidx.room:room-ktx:2.6.1
ksp:room-compiler:2.6.1

// KSP
com.google.devtools.ksp:2.0.21-1.0.27

// التقويم الهجري
com.github.msarhan:umm-al-qura-calendar:2.0.2

// تحميل الصور
io.coil-kt:coil-compose:2.6.0

// الموقع الجغرافي
com.google.android.gms:play-services-location

// أوقات الصلاة
com.batoulapps:adhan:1.2.1

// الخرائط (اختياري)
org.osmdroid:osmdroid-android:6.1.18
```

---

## 🗂️ هيكل المشروع

```
app/src/main/java/com/example/ramadan/
│
├── data/
│   ├── AppDatabase.kt          // Room Database (v3)
│   ├── UserProgress.kt         // Entity: التقدم اليومي
│   ├── Achievement.kt          // Entity: الإنجازات
│   ├── QuranProgress.kt        // Entity: تقدم القرآن
│   ├── UserProgressDao.kt      // DAO: جميع الاستعلامات
│   ├── DashboardViewModel.kt   // ViewModel المركزي
│   ├── QuranData.kt            // بيانات الأجزاء الثلاثين
│   └── AdhkarData.kt           // بيانات الأذكار والورد اليومي
│
├── navigation/
│   └── NavGraph.kt             // تعريف كل الـ routes
│
├── ui/
│   ├── components/
│   │   └── BottomNavBar.kt     // شريط التنقل السفلي
│   │
│   ├── screens/
│   │   ├── WelcomeScreen.kt
│   │   ├── OnboardingScreen.kt
│   │   ├── ProfileSetupScreen.kt
│   │   ├── DhikrScreen.kt      // الشاشة الرئيسية
│   │   ├── AdhkarScreen.kt     // أذكار الصباح والمساء
│   │   ├── QuranScreen.kt      // متابعة الختمة
│   │   └── DashboardScreen.kt  // الإحصائيات
│   │
│   └── theme/
│       ├── Color.kt
│       ├── Type.kt
│       └── Theme.kt
│
└── MainActivity.kt
```

---

## 🚀 تشغيل المشروع

```bash
# استنساخ المستودع
git clone https://github.com/username/ramadan-tracker.git

# فتح المشروع في Android Studio

# تشغيل التطبيق على محاكي أو جهاز حقيقي
# الحد الأدنى: Android API 26 (Android 8.0)
```

---

## 🔧 المتطلبات

- Android Studio Hedgehog أو أحدث
- Kotlin 2.0+
- Android API 26+
- Gradle 8.x

---

## 📋 الإنجازات المدمجة في التطبيق

| الإنجاز | متى يفتح |
|---------|----------|
| 📖 أتممت ختمة القرآن الكريم | عند إكمال الجزء 30 |
| 📿 سبّح X مرة | عند حفظ عداد التسبيح |
| ☀️ أتممت أذكار الصباح | عند إكمال كل أذكار الصباح |
| 🌙 أتممت أذكار المساء | عند إكمال كل أذكار المساء |
| 🌙 أتممت صيام اليوم | عند تسجيل الصيام |

---

## 🌿 Git

```bash
# الفرع الحالي
git checkout feat/welcome-screen

# آخر commit
feat: fix bottom nav navigation and finalize app flow
```

---

<div align="center">

**بُني بـ ❤️ خلال شهر رمضان المبارك**

*اللهم اجعله في ميزان حسناتنا*

</div>
