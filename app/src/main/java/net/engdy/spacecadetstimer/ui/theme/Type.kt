package net.engdy.spacecadetstimer.ui.theme

/**
 * Copyright (c) 2026 Andy Foulke. All rights reserved.
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import net.engdy.spacecadetstimer.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Outfit"),
        fontProvider = provider,
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Outfit"),
        fontProvider = provider,
    )
)

val timerFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Space Mono"),
        fontProvider = provider,
    )
)


// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = timerFontFamily,
        fontSize = 160.sp,
        fontWeight = baseline.displayLarge.fontWeight
    ),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
    titleLarge = TextStyle(
        fontFamily = displayFontFamily,
        fontSize = 48.sp,
        fontWeight = baseline.titleLarge.fontWeight
    ),
    titleMedium = TextStyle(
        fontFamily = displayFontFamily,
        fontSize = 30.sp,
        fontWeight = baseline.titleMedium.fontWeight
    ),
    titleSmall = TextStyle(
        fontFamily = displayFontFamily,
        fontSize = 20.sp,
        fontWeight = baseline.titleSmall.fontWeight
    ),
    bodyLarge = TextStyle(
        fontFamily = bodyFontFamily,
        fontSize = 24.sp,
        fontWeight = baseline.bodyLarge.fontWeight
    ),
    bodyMedium = TextStyle(
        fontFamily = bodyFontFamily,
        fontSize = 16.sp,
        fontWeight = baseline.bodyMedium.fontWeight
    ),
    bodySmall = TextStyle(
        fontFamily = bodyFontFamily,
        fontSize = 12.sp,
        fontWeight = baseline.bodySmall.fontWeight
    ),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
)