package com.app.musicplayer.presentation.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class Spacing(val value: Dp){
    object ContainerSpacing: Spacing(18.dp)// horizontal padding between screen and content
    object RegularSpacing: Spacing(26.dp)// padding between contents
    object ExtraSpacing: Spacing(80.dp)//padding between top of screen and content.eg: size of a top bar
}
