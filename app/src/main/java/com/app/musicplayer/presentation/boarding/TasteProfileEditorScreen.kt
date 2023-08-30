package com.app.musicplayer.presentation.boarding

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.musicplayer.R
import com.app.musicplayer.presentation.theme.inter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


data class TasteIdentifier(
    val name: String,
    val items: List<TasteIdentifierItem>
)
data class TasteIdentifierItem(
    val id : String,
    val item: String
)
data class UserTasteData(
    var genre: String,
    var mood: String,
    var decade: String
)
sealed class TasteIdentifingFactor(val string: String){
    object Genre : TasteIdentifingFactor("Genre")
    object Mood : TasteIdentifingFactor("Moods")
    object Decade : TasteIdentifingFactor("Decades")
}

@Composable
fun TasteProfileEditorScreen(
    onSubmit: (UserTasteData) -> Unit
){
    var genreOption: String? by remember{
        mutableStateOf(null)
    }
    var moodOption: String? by remember{
        mutableStateOf(null)
    }
    var decadeOption: String? by remember{
        mutableStateOf(null)
    }
    val scrollState =rememberScrollState()
    val scope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        MaterialTheme.colorScheme.primary
                    ),
                    startY = 0f, endY = Float.POSITIVE_INFINITY
                )
            )
            .padding(top = 40.dp, start = 20.dp, end = 15.dp)
    ) {
        Text(
            text = stringResource(id = R.string.TasteEditorScreen_title),
            color = MaterialTheme.colorScheme.secondary,
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = inter,
                fontWeight = FontWeight.Black,
            )
            )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = stringResource(id = R.string.TasteEditorScreen_description),
            color = MaterialTheme.colorScheme.secondary,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = inter,
                fontWeight = FontWeight.W500,
            ),
            modifier = Modifier.padding(end = 45.dp)
        )
        Spacer(modifier = Modifier.height(45.dp))

        identifiers.forEach{ tasteIdentifier->
            Text(
                text = tasteIdentifier.name,
                color = MaterialTheme.colorScheme.secondary,
                style = TextStyle(
                    fontSize = 25.sp,
                    fontFamily = inter,
                    fontWeight = FontWeight.W800,
                )
                )
            Spacer(modifier = Modifier.height(18.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.heightIn(max = 200.dp),
                userScrollEnabled = false
                ){
                items(items = tasteIdentifier.items){
                    OutlinedIconButton(
                        onClick = {
                            when(tasteIdentifier.name){
                                      TasteIdentifingFactor.Genre.string -> {
                                          genreOption = if (genreOption == it.id) null else it.id
                                          scope.launch{
                                              scrollState.animateScrollTo(0)
                                          }
                                      }
                                      TasteIdentifingFactor.Mood.string -> moodOption = if(moodOption == it.id) null else it.id
                                      TasteIdentifingFactor.Decade.string -> {
                                          decadeOption = if (decadeOption == it.id) null else it.id
                                          scope.launch{
                                              scrollState.animateScrollTo(scrollState.maxValue)
                                          }
                                      }
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        border = when(tasteIdentifier.name){
                            TasteIdentifingFactor.Genre.string -> if(genreOption == it.id) BorderStroke(2.dp,MaterialTheme.colorScheme.secondaryContainer) else BorderStroke(1.dp,Color.Gray)
                            TasteIdentifingFactor.Mood.string -> if(moodOption == it.id) BorderStroke(2.dp,MaterialTheme.colorScheme.secondaryContainer) else BorderStroke(1.dp,Color.Gray)
                            TasteIdentifingFactor.Decade.string -> if(decadeOption == it.id) BorderStroke(2.dp,MaterialTheme.colorScheme.secondaryContainer) else BorderStroke(1.dp,Color.Gray)
                            else -> {
                                BorderStroke(1.dp,Color.Gray)
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = when(tasteIdentifier.name){
                                TasteIdentifingFactor.Genre.string -> if(genreOption == it.id) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
                                TasteIdentifingFactor.Mood.string -> if(moodOption == it.id) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
                                TasteIdentifingFactor.Decade.string -> if(decadeOption == it.id) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
                                else -> {
                                    Color.Transparent
                                }
                            },
                            contentColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(
                            text = it.item,
                            color = MaterialTheme.colorScheme.secondary,
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontFamily = inter,
                                fontWeight = FontWeight.W600,
                            )
                            )
                    }
                }
            }
            Spacer(modifier = Modifier.height(27.dp))
        }
        FilledTonalButton(
            onClick = {
                      if((genreOption != null) && (moodOption != null) && (decadeOption != null)){
                          val submittedData = UserTasteData(
                              genre = genreOption!!,
                              mood = moodOption!!,
                              decade = decadeOption!!
                          )
                          onSubmit.invoke(submittedData)

                      }
            },
            enabled= genreOption != null && moodOption != null && decadeOption != null,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
                .padding(bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary,
                disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                disabledContentColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(10.dp)
            ) {
            Text(
                text = "Continue",
                )
        }

    }
}

val identifiers = listOf(
    TasteIdentifier(
        name = "Genre",
        items = listOf(
            TasteIdentifierItem(
                id = "hiphop",
                item = "Hip Hop"
            ),
            TasteIdentifierItem(
                id = "pop",
                item = "Pop"
            ),
            TasteIdentifierItem(
                id = "dance",
                item = "Dance"
            ),
            TasteIdentifierItem(
                id = "jazz",
                item = "Jazz"
            ),
            TasteIdentifierItem(
                id = "r&b",
                item = "R&B"
            ),
            TasteIdentifierItem(
                id = "classical",
                item = "Classical"
            ),
            TasteIdentifierItem(
                id = "metal",
                item = "Metal"
            )

        )
    ),
    TasteIdentifier(
        name = "Moods",
        items = listOf(
            TasteIdentifierItem(
                id = "happy",
                item = "Happy"
            ),
            TasteIdentifierItem(
                id = "sad",
                item = "Sad"
            ),
            TasteIdentifierItem(
                id = "energetic",
                item = "Energetic"
            ),
            TasteIdentifierItem(
                id = "relaxing",
                item = "Relaxing"
            ),
            TasteIdentifierItem(
                id = "chill",
                item = "Chill"
            ),
            TasteIdentifierItem(
                id = "aggressive",
                item = "Melancholic"
            ),

            )
    ),
    TasteIdentifier(
        name = "Decades",
        items = listOf(
            TasteIdentifierItem(
                id = "2020",
                item = "2020s"
            ),
            TasteIdentifierItem(
                id = "2010",
                item = "2010s"
            ),
            TasteIdentifierItem(
                id = "2000",
                item = "2000s"
            ),
            TasteIdentifierItem(
                id = "1990",
                item = "1990s"
            ),
            TasteIdentifierItem(
                id = "1980",
                item = "1980s"
            )
        )
    )

)