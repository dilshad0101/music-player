package com.app.musicplayer.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.app.musicplayer.R


val inter = FontFamily(
    Font(R.font.inter_black, FontWeight.Black),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_seminbold, FontWeight.SemiBold),
    Font(R.font.inter_thin, FontWeight.Thin)
)

val Typography = Typography(
    headlineMedium = TextStyle(
        fontFamily = inter,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = TextStyle(
        fontFamily = inter,
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleLarge = TextStyle(
        fontFamily = inter,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = TextStyle(
        fontFamily = inter,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleSmall = TextStyle(
        fontFamily = inter,
        fontSize = 15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = inter,
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium
    ),
    bodySmall = TextStyle(
        fontFamily = inter,
        fontSize = 15.sp
    ),
    displayLarge = TextStyle(
        fontFamily = inter,
        fontSize = 26.sp,
        fontWeight = FontWeight.SemiBold
    ),
    displayMedium = TextStyle(
        fontFamily = inter,
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold
    ),
    displaySmall = TextStyle(
        fontFamily = inter,
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold
    )
)