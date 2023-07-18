package com.app.musicplayer.presentation.screen.player

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderBar(value : Float,
              onValueChange: (Float) -> Unit,
              max: Long,
              onValueChangeFinished: () -> Unit
              ){
    val valueRange = 0f..max.toFloat()
    Slider(
        value = value,
        valueRange = valueRange,
        onValueChange = {valueChange->
            onValueChange.invoke(valueChange)},
        onValueChangeFinished = {
            onValueChangeFinished.invoke()
        },
        colors = SliderDefaults.colors(
            thumbColor = Color.Transparent,
            inactiveTrackColor = Color(0x66FAFAFA),
            activeTrackColor = Color(0xBEFAFAFA)
        ),
        modifier = Modifier.height(12.dp),
        thumb = {},
        track = {
            ModifiedSliderTrack(
                sliderPositions = it,
                modifier = Modifier.height(10.dp)
                )
        }

        )
}
@Composable
fun ModifiedSliderTrack(
    sliderPositions: SliderPositions,
    modifier: Modifier = Modifier,
    colors: SliderColors = SliderDefaults.colors(),
    enabled: Boolean = true,
) {
    val inactiveTrackColor = Color(0x66FAFAFA)
    val activeTrackColor = Color(0xBEFAFAFA)
    val inactiveTickColor = Color.Gray
    val activeTickColor = Color.Gray
    Canvas(
        modifier
            .fillMaxWidth()
            .height(10.dp)
    ) {
        val isRtl = layoutDirection == LayoutDirection.Rtl
        val sliderLeft = Offset(0f, center.y)
        val sliderRight = Offset(size.width, center.y)
        val sliderStart = if (isRtl) sliderRight else sliderLeft
        val sliderEnd = if (isRtl) sliderLeft else sliderRight
        val tickSize = 0.dp.toPx()
        val trackStrokeWidth = 10.dp.toPx()
        drawLine(
            inactiveTrackColor,
            sliderStart,
            sliderEnd,
            trackStrokeWidth,
            StrokeCap.Round
        )
        val sliderValueEnd = Offset(
            sliderStart.x +
                    (sliderEnd.x - sliderStart.x) * sliderPositions.activeRange.endInclusive,
            center.y
        )

        val sliderValueStart = Offset(
            sliderStart.x +
                    (sliderEnd.x - sliderStart.x) * sliderPositions.activeRange.start,
            center.y
        )

        drawLine(
            activeTrackColor,
            sliderValueStart,
            sliderValueEnd,
            trackStrokeWidth,
            StrokeCap.Round
        )
        sliderPositions.tickFractions.groupBy {
            it > sliderPositions.activeRange.endInclusive ||
                    it < sliderPositions.activeRange.start
        }.forEach { (outsideFraction, list) ->
            drawPoints(
                list.map {
                    Offset(lerp(sliderStart, sliderEnd, it).x, center.y)
                },
                PointMode.Points,
                (if (outsideFraction) inactiveTickColor else activeTickColor),
                tickSize,
                StrokeCap.Round
            )
        }
    }
}
