package com.app.musicplayer.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.musicplayer.R
import com.app.musicplayer.presentation.theme.Spacing


@Preview
@Composable
fun HomeTopBar(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = Spacing.ContainerSpacing.value)
            .height(Spacing.ExtraSpacing.value)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.notification),
                contentDescription = "Notifications.",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
                )

            Icon(
                painter = painterResource(id = R.drawable.musics),
                contentDescription = "Discover what your friends are currently listening to.")




        }



    }
}