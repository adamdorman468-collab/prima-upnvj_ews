package com.fik.upnvj.ews.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.upnvj.prima.R

private val HeadingFontFamily = FontFamily(
    Font(R.font.montserrat_variablefont_wght, weight = FontWeight.W400),
    Font(R.font.montserrat_variablefont_wght, weight = FontWeight.W600),
    Font(R.font.montserrat_variablefont_wght, weight = FontWeight.W700),
    Font(R.font.montserrat_italic_variablefont_wght, weight = FontWeight.W400, style = FontStyle.Italic),
    Font(R.font.montserrat_italic_variablefont_wght, weight = FontWeight.W600, style = FontStyle.Italic),
    Font(R.font.montserrat_italic_variablefont_wght, weight = FontWeight.W700, style = FontStyle.Italic)
)

private val BodyFontFamily = FontFamily(
    Font(R.font.inter_variablefont_opsz_wght, weight = FontWeight.W400),
    Font(R.font.inter_variablefont_opsz_wght, weight = FontWeight.W500),
    Font(R.font.inter_variablefont_opsz_wght, weight = FontWeight.W700),
    Font(R.font.inter_italic_variablefont_opsz_wght, weight = FontWeight.W400, style = FontStyle.Italic),
    Font(R.font.inter_italic_variablefont_opsz_wght, weight = FontWeight.W500, style = FontStyle.Italic),
    Font(R.font.inter_italic_variablefont_opsz_wght, weight = FontWeight.W700, style = FontStyle.Italic)
)

val PrimaTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = HeadingFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 48.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = HeadingFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = HeadingFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = HeadingFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = BodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = BodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = BodyFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = BodyFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = BodyFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
)
