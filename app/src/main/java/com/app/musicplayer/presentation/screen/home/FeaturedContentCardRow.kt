package com.app.musicplayer.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.app.musicplayer.data.featuredContent.FeaturedContent
import com.app.musicplayer.presentation.theme.Spacing
import com.app.musicplayer.presentation.utility.ImageLoader

@Composable
fun FeaturedContentCardRow(contentList : List<FeaturedContent>){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = PaddingValues(horizontal = Spacing.ContainerSpacing.value),
        modifier = Modifier
    ){
        this.items(contentList){
            ImageLoader(
                url = it.imageUrl,
                modifier = Modifier
                    .fillMaxWidth().height(200.dp)
                    .clip(RoundedCornerShape(10))
                    .clickable {},
                contentDescription = "Featured Contents"
            )
        }
    }


}