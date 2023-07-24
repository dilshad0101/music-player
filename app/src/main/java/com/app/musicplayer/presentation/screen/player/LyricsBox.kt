package com.app.musicplayer.presentation.screen.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.musicplayer.R

@Composable
fun LyricsBox(
    brush: Brush,
    onClick:() -> Unit
              ){
    Box(modifier = Modifier
        .size(300.dp)
        .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
        .background(
            brush = brush,
            shape = RoundedCornerShape(10),
            alpha = 0.9f
            )
        .clickable {
            onClick.invoke()
        }
        ) {
        Column(
            modifier = Modifier.padding(
                top = 10.dp,
                start= 22.dp,
                end = 22.dp,
                bottom = 26.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Lyrics")
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id =R.drawable.maximize),
                        contentDescription = "maximize lyrics box",
                        modifier = Modifier.size(20.dp))
                }
            }
            LazyColumn(){
                item {
                    val text = "I'm holding my breath and watching my step I'm listing regrets and you made that list you're my depressionYour first impression, wasn't deceptionYou were lyin' Bitch, I'm still flexin' with my heart broken Got my heart open, I'm not high yetBitch, I'm still moving, I'm in slow motion I wrote my dosage, I'm getting higher"
                    Text(
                        text = text,
                        softWrap = true,
                        style = MaterialTheme.typography.displayMedium
                    )

                }
            }
        }
    }
}