package com.app.musicplayer.presentation.boarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.musicplayer.R
import com.app.musicplayer.presentation.theme.inter

@Preview
@Composable
fun BoardingScreen(){
    //TODO : Create a box with Image
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 30.dp)
            .wrapContentHeight(Alignment.Bottom)
    ){
        Text(
            text = R.string.app_name.toString(),
            style = TextStyle(
                fontFamily = inter,
                fontWeight = FontWeight.Black,
                fontSize = 36.sp,
                color = MaterialTheme.colorScheme.secondary,
            )
            )
        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = R.string.BoardingScreen_app_description.toString(),
            style = TextStyle(
                fontFamily = inter,
                fontWeight = FontWeight.W500,
                fontStyle = FontStyle.Italic,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.secondary,
            ),
            textAlign = TextAlign.Start
            )
        Spacer(modifier = Modifier.height(64.dp))
        //TODO: Continue With Google Button To be Implemented
    }
}