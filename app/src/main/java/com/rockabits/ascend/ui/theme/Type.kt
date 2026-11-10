@file:OptIn(androidx.compose.ui.text.ExperimentalTextApi::class)

package com.rockabits.ascend.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import com.rockabits.ascend.R

/** Títulos (no Figma: Fredoka One; usamos a Fredoka, que a substituiu). */
val Display = FontFamily(
    Font(
        resId = R.font.fredoka,
        weight = FontWeight.SemiBold,
        variationSettings = FontVariation.Settings(FontVariation.weight(600)),
    )
)

/** Texto corrente (Instrument Sans). */
val Body = FontFamily(Font(R.font.instrument_sans))

private val base = Typography()

val AscendTypography = Typography(
    bodyLarge = base.bodyLarge.copy(fontFamily = Body),
    bodyMedium = base.bodyMedium.copy(fontFamily = Body),
    bodySmall = base.bodySmall.copy(fontFamily = Body),
    labelLarge = base.labelLarge.copy(fontFamily = Body),
    labelMedium = base.labelMedium.copy(fontFamily = Body),
    labelSmall = base.labelSmall.copy(fontFamily = Body),
    titleMedium = base.titleMedium.copy(fontFamily = Display),
    titleLarge = base.titleLarge.copy(fontFamily = Display),
)
