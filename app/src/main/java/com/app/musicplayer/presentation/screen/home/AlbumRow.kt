package com.app.musicplayer.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.app.musicplayer.data.track.Album
import com.app.musicplayer.presentation.theme.Spacing
import com.app.musicplayer.presentation.utility.ImageLoader

@Composable
fun AlbumRow(
    title: String,
    albumList: List<Album>,
    onClick: (String) -> Unit
){
    Column(
        modifier =
        Modifier
            .padding(
                top = Spacing.RegularSpacing.value,
                bottom = 5.dp,
            )
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(start = Spacing.ContainerSpacing.value)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 10.dp),
            contentPadding = PaddingValues(start = Spacing.ContainerSpacing.value)
        ){
            items(items = albumList){
                Column {
                    ImageLoader(
                        url = it.coverArtUrl,
                        contentDescription = "Album ${it.title}",
                        modifier = Modifier
                            .height(120.dp)
                            .width(120.dp)
                            .clip(RoundedCornerShape(10))
                            .clickable{onClick.invoke(it.title)}
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.titleMedium)
                }
                
            }
        }
    }
}