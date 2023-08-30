package com.app.musicplayer.presentation.boarding

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.app.musicplayer.presentation.theme.inter

val boardingScreenTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight.Black,
        fontSize = 36.sp,

    ),
    displaySmall = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight.W500,
        fontStyle = FontStyle.Italic,
        fontSize = 17.sp,
    )
)